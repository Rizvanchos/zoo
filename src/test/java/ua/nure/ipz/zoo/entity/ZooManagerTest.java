package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ZooManagerTest {
    private static final String NAME = "Name";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "Password";

    @Mock
    private Provision mockProvision;

    @Test
    public void shouldCreateZooManagerWithParameters_whenCreateZooManagerWithParameters(){
        ZooManager zooManager = new ZooManager(NAME, EMAIL, PASSWORD);
        assertEquals(NAME, zooManager.getName());
        assertEquals(EMAIL, zooManager.getEmail());
        assertEquals(PASSWORD, zooManager.getPassword());
    }

    @Test
    public void shouldContainPassedProvision_whenAssignProvision() throws Exception {
        ZooManager zooManager = new ZooManager();
        zooManager.assignProvision(mockProvision);
        assertTrue(zooManager.getProvisions().contains(mockProvision));
    }

    @Test
    public void shouldSetZooManagerNewProvisions_whenSetNewProvisions(){
        ZooManager zooManager = new ZooManager();
        List<Provision> provisions = Collections.singletonList(mockProvision);
        zooManager.setProvisions(provisions);

        assertEquals(provisions, zooManager.getProvisions());
    }
}
