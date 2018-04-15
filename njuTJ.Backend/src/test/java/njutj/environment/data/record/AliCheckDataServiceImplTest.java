package njutj.environment.data.record;

import njutj.environment.dataservice.record.AliCheckDataService;
import njutj.environment.exception.viewexception.SystemException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliCheckDataServiceImplTest {
    @Autowired
    private AliCheckDataService aliCheckDataService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void checkImage() {
        try {
            String result = aliCheckDataService.checkImage("https://www.ritagiang.com/files/articleFilesfinder/images/IMG_2085-1.jpg");
            System.out.println(result);
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }
}