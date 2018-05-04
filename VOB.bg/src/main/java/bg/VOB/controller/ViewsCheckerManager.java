package bg.VOB.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import bg.VOB.model.dao.UserDao;
import util.validation.VideoViewsThread;

@Component
public class ViewsCheckerManager{
	
	private HashMap<String, ArrayList<String>> sessionVideoViews = new HashMap<>();
	
	private static ViewsCheckerManager instance;
	
	private ViewsCheckerManager() {}
	
	public static synchronized ViewsCheckerManager getInstance() {
		if(instance == null) {
			instance = new ViewsCheckerManager();
		}
		return instance;
	}
	
	
	public boolean areVideoViewsForIncrement(String path,String username) {
		synchronized (username) {
			boolean forIncrement = false;
			if(sessionVideoViews.containsKey(username)) {
				ArrayList<String> videosWatched = sessionVideoViews.get(username);
				if(videosWatched.isEmpty() || !(videosWatched.contains(path))) {
					sessionVideoViews.get(username).add(path);
					forIncrement = true;
				}
			}else {
				sessionVideoViews.put(username, new ArrayList<>());
				sessionVideoViews.get(username).add(path);
				forIncrement = true;
			}
			
			if(forIncrement) {
				Thread count = new Thread(new VideoViewsThread(path,username));
				count.setDaemon(true);
				count.start();
			}
			
			return forIncrement;
		}
			
	}
	
	public void addVideoForViewsBlock(String path,String username) {
		System.out.println("purvi put");
		sessionVideoViews.put(username, new ArrayList<>());
		sessionVideoViews.get(username).add(path);
		System.out.println("purvi put po4vam");
		Thread count = new Thread(new VideoViewsThread(path,username));
		count.setDaemon(true);
		count.start();
		System.out.println("purvi put po4nah");
	}
	
	public void viewsTimeExp(String path,String username) {
		if(sessionVideoViews.containsKey(username)) {
			System.out.println("maham go ot kolekciqta naistina");
			sessionVideoViews.get(username).remove(path);
		}
	}
	
	
}
