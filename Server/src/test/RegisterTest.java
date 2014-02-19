package test;

import com.beihua.xinggh.xghqq.server.impl.UserDaoFactory;
import com.beihua.xinggh.xghqq.server.itf.IUserDao;
import com.beihua.xinggh.xghqq.server.vo.User;

public class RegisterTest {

	public static void main(String[] args) {
		IUserDao dao = UserDaoFactory.getInstance();
		User u = new User();
		u.setUid(1000000000);
		u.setPassword("123");
		u.setName("hjjh");
		u.setAge(23);
		u.setSex("ÄÐ");
		dao.register(u);
	}
}
