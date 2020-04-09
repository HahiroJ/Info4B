package Server;

import java.util.HashMap;

//cette classe est un singleton
public class FileCache {

    private HashMap<String,String> cache;

    private FileCache() {
        cache = new HashMap<>();
    }

    private static class FileCacheHolder {
        private final static FileCache instance = new FileCache();
    }

    public static FileCache getInstance() {
        return FileCacheHolder.instance;
    }

    synchronized public void put(String key, String value) {
        cache.put(key, value);
    }
    
    synchronized public void remove(String key) {
        cache.remove(key);
    }

    synchronized public String getFile(String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        else {
            System.err.println("Error, files doesn't exit in cache");
            return null;
        }
    }
}