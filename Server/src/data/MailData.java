package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.MailDataService;
import po.MailPO;
import po.UserPO;

public class MailData extends UnicastRemoteObject implements MailDataService {

	protected MailData() throws RemoteException {
		super();
	}

	@Override
	public boolean saveMail(MailPO mail) throws RemoteException {
		try{
			String sql="INSERT INTO MailInfo VALUES('"+mail.getFromId()+"','"+mail.getToId()
			+"','"+mail.getTime()+"','"+mail.getContent()+"',"+mail.isRead()+");";
			Statement s=DataHelper.getInstance().createStatement();
			int r=s.executeUpdate(sql);
			if(r>0)return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public ArrayList<MailPO> getMailList(UserPO user) throws RemoteException {
		ArrayList<MailPO> mails=new ArrayList<MailPO>();
		try{
			String sql="SELCET * FROM MailInfo WHERE MIFromID='"+user.getUserId()+"' OR MIToID='"+user.getUserId()+"';";
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery(sql);
			while(r.next()){
				MailPO mail=new MailPO(
						r.getString("MIFromID"),
						r.getString("MIToID"),
						r.getString("MIContent"),
						r.getString("MITime"),
						r.getBoolean("MIIsExist")
						);
				mails.add(mail);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return mails;
	}

	@Override
	public boolean readMail(MailPO mail) throws RemoteException {
		try{
			String sql="UPDATE MailInfo SET MIIsExist="+1;
			Statement s=DataHelper.getInstance().createStatement();
			int r=s.executeUpdate(sql);
			if(r>0)return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
