package com.laurencetrippen.jacl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Properties;

import com.laurencetrippen.jacl.reflect.ConfigFile;
import com.laurencetrippen.jacl.reflect.ConfigProperty;

public class ConfigManager<T> {

	private Class<T> type;

	public ConfigManager(Class<T> type) {
		this.type = type;
	}

	public T generateConfig() {
		if (type.isAnnotationPresent(ConfigFile.class)) {
			ConfigFile configFile = type.getAnnotation(ConfigFile.class);
			File file = new File(configFile.path());

			if (!file.exists()) {
				Field[] fields = type.getDeclaredFields();
				Properties properties = new Properties();

				for (Field field : fields) {
					if (field.isAnnotationPresent(ConfigProperty.class)) {
						properties.setProperty(field.getName(), "");
					}
				}

				try {
					properties.store(new FileOutputStream(file), null);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Datei existiert schon!");
			}
		} else {
			System.out.println("Typ besitzt keine ConfigFile Annotation!");
		}

		return null;
	}

	public void save(T type) {
		if (type.getClass().isAnnotationPresent(ConfigFile.class)) {
			ConfigFile configFile = type.getClass().getAnnotation(ConfigFile.class);
			Properties properties = new Properties();

			Field[] fields = type.getClass().getDeclaredFields();
			Method[] methods = type.getClass().getDeclaredMethods();

			for (Field field : fields) {
				if (field.isAnnotationPresent(ConfigProperty.class)) {
					if (field.getType() == boolean.class) {
						String getter = "is" + field.getName().toLowerCase();
						for (Method method : methods) {
							if (Modifier.isPublic(method.getModifiers())) {
								if (method.getName().toLowerCase().equals(getter)) {
									try {
										boolean b = (boolean) method.invoke(type);
										properties.setProperty(field.getName(), String.valueOf(b));
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										e.printStackTrace();
									}
								}
							}
						}
					} else {
						String getter = "get" + field.getName().toLowerCase();
						for (Method method : methods) {
							if (Modifier.isPublic(method.getModifiers())) {
								if (method.getName().toLowerCase().equals(getter)) {
									try {
										Class<?> returnType = method.getReturnType();
										
										if (returnType.equals(Integer.TYPE)) {
											int i = (int) method.invoke(type);
											properties.setProperty(field.getName(), String.valueOf(i));
										} else if (returnType.equals(Float.TYPE)) {
											float f = (float) method.invoke(type);
											properties.setProperty(field.getName(), String.valueOf(f));
										} else if (returnType.equals(Double.TYPE)) {
											double d = (double) method.invoke(type);
											properties.setProperty(field.getName(), String.valueOf(d));
										} else if (returnType == String.class) {
											String s = (String) method.invoke(type);
											properties.setProperty(field.getName(), s);
										}
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			
			try {
				properties.store(new FileOutputStream(new File(configFile.path())), null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public T load() {
		T instance = null;
		if (type.isAnnotationPresent(ConfigFile.class)) {
			ConfigFile configFile = type.getAnnotation(ConfigFile.class);
			File file = new File(configFile.path());

			if (file.exists()) {
				Field[] fields = type.getDeclaredFields();
				Method[] methods = type.getDeclaredMethods();

				Properties properties = new Properties();
				try {
					properties.load(new FileInputStream(file));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				try {
					instance = type.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				for (Field field : fields) {
					if (field.isAnnotationPresent(ConfigProperty.class)) {
						String setter = "set" + field.getName().toLowerCase();
						for (Method method : methods) {
							if (Modifier.isPublic(method.getModifiers())) {
								if (method.getName().toLowerCase().equals(setter)) {
									try {
										Class<?>[] params = method.getParameterTypes();
										if (params[0].equals(int.class)) {
											Method m = type.getMethod(method.getName(), int.class);
											m.invoke(instance, Integer
													.parseInt(properties.getProperty(field.getName()).equals("") ? "0"
															: properties.getProperty(field.getName())));
										} else if (params[0].equals(float.class)) {
											Method m = type.getMethod(method.getName(), float.class);
											m.invoke(instance, Float.parseFloat(
													properties.getProperty(field.getName()).equals("") ? "0.0f"
															: properties.getProperty(field.getName())));
										} else if (params[0].equals(double.class)) {
											Method m = type.getMethod(method.getName(), double.class);
											m.invoke(instance, Double.parseDouble(
													properties.getProperty(field.getName()).equals("") ? "0.0"
															: properties.getProperty(field.getName())));
										} else if (params[0].equals(boolean.class)) {
											Method m = type.getMethod(method.getName(), boolean.class);
											m.invoke(instance, Boolean.parseBoolean(
													properties.getProperty(field.getName()).equals("") ? "false"
															: properties.getProperty(field.getName())));
										} else if (params[0].equals(String.class)) {
											Method m = type.getMethod(method.getName(), String.class);
											m.invoke(instance, properties.getProperty(field.getName()));
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			} else {
				System.out.println("Datei existiert nicht!");
			}
		} else {
			System.out.println("Typ besitzt keine ConfigFile Annotation!");
		}

		return instance;
	}
}