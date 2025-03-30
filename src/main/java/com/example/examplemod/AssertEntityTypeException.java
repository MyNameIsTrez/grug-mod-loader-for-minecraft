package com.example.examplemod;

public class AssertEntityTypeException extends RuntimeException {
    public AssertEntityTypeException(EntityType entityType, EntityType expectedEntityType) {
        super("Expected " + getSnakeCase(expectedEntityType) + ", but got " + getSnakeCase(entityType));
    }

    private static String getSnakeCase(EntityType entityType) {
        StringBuilder result = new StringBuilder();
        boolean firstCharacter = true;
        for (char c : entityType.toString().toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (!firstCharacter) {
                    result.append("_");
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
            firstCharacter = false;
        }
        return result.toString();
    }
}
