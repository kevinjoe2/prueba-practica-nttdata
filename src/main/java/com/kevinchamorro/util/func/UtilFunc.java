package com.kevinchamorro.util.func;

public class UtilFunc {
	
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T getEnumFromString(Class<T> enumClass, String value) {
		
		if (enumClass == null) {
			throw new IllegalArgumentException("EnumClass value can't be null.");
		}

		for (Enum<?> enumValue : enumClass.getEnumConstants()) {
			if (enumValue.toString().equalsIgnoreCase(value)) {
				return (T) enumValue;
			}
		}

		// Construct an error message that indicates all possible values for the enum.
		StringBuilder errorMessage = new StringBuilder();
		boolean bFirstTime = true;
		for (Enum<?> enumValue : enumClass.getEnumConstants()) {
			errorMessage.append(bFirstTime ? "" : ", ").append(enumValue);
			bFirstTime = false;
		}
		throw new IllegalArgumentException(value + " no es valido, los valores aceptados son " + errorMessage);
		
	}
	
}
