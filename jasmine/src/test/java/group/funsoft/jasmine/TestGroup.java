package group.funsoft.jasmine;

public class TestGroup {
    private Long groupId;
    private String value;
     
    private TestGroup(Long groupId, String value) {
        this.groupId = groupId;
        this.value = value;
    }
    
    public static TestGroup get(Long groupId, String value) {
        return new TestGroup(groupId, value);
    }
    
    public Long getGroupId() {
        return this.groupId;
    }
    
    public String value() {
        return this.value;
    }
}
