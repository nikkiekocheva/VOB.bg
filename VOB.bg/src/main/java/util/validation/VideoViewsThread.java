package util.validation;

import bg.VOB.controller.ViewsCheckerManager;

public class VideoViewsThread implements Runnable{

	private String path;
	private String username;
	
	public VideoViewsThread(String path,String username) {
		this.path = path;
		this.username = username;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(120000);
			ViewsCheckerManager.getInstance().viewsTimeExp(this.path, this.username);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
