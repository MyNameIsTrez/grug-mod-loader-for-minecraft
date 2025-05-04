package grug.grugmodloader;

public class StringUtils {
    public static String getSnakeCase(EntityType entityType) {
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
