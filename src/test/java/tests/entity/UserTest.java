package tests.entity;

import org.junit.Test;

import main.domain.User;
import main.domain.UserGroup;
import main.domain.enums.Permission;

import static org.junit.Assert.assertEquals;

/**
 * @author Sam
 */
public class UserTest {

    @Test
    public void testGettersSetters(){
        UserGroup userGroup = new UserGroup();
        userGroup.setId(2L);

        User user = new User();
        user.setId(1L);
        user.setPassword("stroomkabel21");
        user.getPermissions().add(Permission.KM_PRICE);
        user.setUsername("cakeje99");
        user.setUserGroup(userGroup);


        assertEquals(new Long(1L), user.getId());
        assertEquals("stroomkabel21", user.getPassword());
        assertEquals(Permission.KM_PRICE, user.getPermissions().get(0));
        assertEquals("cakeje99", user.getUsername());
        assertEquals(userGroup.getId(), user.getUserGroup().getId());
    }

}
