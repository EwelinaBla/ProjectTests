package Utils;

public class TestDataReader extends FileReader {
    private String testDataLocation;

    private TestData testData;

    public TestDataReader(String testDataLocation) {
        super(testDataLocation);
        this.testDataLocation=testDataLocation;
    }

    void loadData() {
        testData=new TestData(properties);
    }

    public String getTestDataLocation() {
        return testDataLocation;
    }
    public TestData getTestData() {
        return testData;
    }

}
