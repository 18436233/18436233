package com.chatclient.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

import com.yychat.model.Message;
import com.yychatclient.control.ClientConnet;

public class FriendChatClient extends JFrame implements ActionListener,Runnable{

	
	//
	JScrollPane jsp;
	JTextArea jta;

	
	//
	JPanel jp;
	JTextField jtf;
	JButton jb;
	
	String sender;
	String receiver;
	public FriendChatClient(String sender, String receiver){
		this.sender=sender;
		this.receiver=receiver;
		
		jta = new JTextArea();
		jta.setEditable(false);
		jsp =new JScrollPane(jta);
		this.add(jsp,"Center");
		
		
		jp =new JPanel();
		jtf =new JTextField(15);
		jtf.setForeground(Color.red);
		jb=new JButton("����");
		jb.addActionListener(this);
		jp.add(jtf);
		jp.add(jb);
		this.add(jp,"South");
		
		
		this.setSize(350,240);
		this.setTitle(sender+"���ں�"+receiver+"����");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	
	}
	public static void main(String[] args) {
		//FriendChatClient FriendChatClient=new FriendChatClient();


	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==jb) {
			jta.append(jtf.getText()+"\r\n");
			
			//
			
			
			Message mess = new Message();
			mess.setSender(sender);
			mess.setReceiver(receiver);
			mess.setContent(jtf.getText());
			mess.setMessageType(Message.message_Common);
			ObjectOutputStream oos;
			try {
				
				oos = new ObjectOutputStream(ClientConnet.s.getOutputStream());
				oos.writeObject(mess);
				//
			} catch (IOException e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
			
		}
			
		
	}

	@Override
	public void run() {
		while(true){
		try {
			ObjectInputStream ois= new ObjectInputStream(ClientConnet.s.getInputStream());
			Message mess = (Message)ois.readObject();
			String showMessage=mess.getSender()+"��"+mess.getReceiver()+"˵��"+mess.getContent();
			System.out.println(showMessage);
			jta.append(showMessage+"\r\n");
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	}
}