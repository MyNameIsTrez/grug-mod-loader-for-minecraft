package grug.grugmodloader;

public class AssertEntityTypeException extends Exception {
    public AssertEntityTypeException(GrugEntityType entityType, GrugEntityType expectedEntityType) {
        super("Expected " + StringUtils.getSnakeCase(expectedEntityType) + ", but got " + StringUtils.getSnakeCase(entityType));
    }

    public AssertEntityTypeException(GrugEntityType entityType) {
        super("Expected iterable, but got " + StringUtils.getSnakeCase(entityType));
    }
}
