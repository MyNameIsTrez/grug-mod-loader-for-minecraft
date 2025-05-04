package grug.grugmodloader;

public class AssertEntityTypeException extends RuntimeException {
    public AssertEntityTypeException(EntityType entityType, EntityType expectedEntityType) {
        super("Expected " + StringUtils.getSnakeCase(expectedEntityType) + ", but got " + StringUtils.getSnakeCase(entityType));
    }
}
