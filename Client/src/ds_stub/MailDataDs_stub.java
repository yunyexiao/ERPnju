package ds_stub;

import java.util.ArrayList;

import dataservice.MailDataService;
import po.MailPO;
import po.UserPO;

public class MailDataDs_stub implements MailDataService {

	@Override
	public boolean saveMail(MailPO mail) {
		return true;
	}

	@Override
	public ArrayList<MailPO> getMailList(UserPO user) {
		System.out.println("�õ�һ�������ʼ�");
		ArrayList<MailPO> list = new ArrayList<MailPO>();
		list.add(new MailPO("0002", "0002", "�������칫��һ��", "2017-12-19 15:03:20", true));
		list.add(new MailPO("0003", "0002", "����¥�¸����Ҽ�", "2017-12-29 15:03:20", true));
		list.add(new MailPO("0001", "0002", "uuuuuu", "2017-12-29 05:03:20", true));
		list.add(new MailPO("0002", "0002", "��������ȥ����ĺ���", "2017-12-31 16:23:20", false));
		list.add(new MailPO("0000", "0000", "�������", "2017-12-30 11:32:00", false));
		list.add(new MailPO("0004", "0000", "van", "2017-12-30 01:32:00", false));
		list.add(new MailPO("0000", "0000", "hhhhh", "2017-12-30 11:33:00", false));
		return list;
	}

	@Override
	public boolean readMail(MailPO mail) {
		return true;
	}

}
