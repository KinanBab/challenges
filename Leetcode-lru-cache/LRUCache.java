// Least recently used cache with a limited capacity
// When capacity is reached, and a new element is to be added
// the element least recently used must be evicted!
// This implements both get and put in O(1)
// https://leetcode.com/problems/lru-cache/submissions/

class LRUCache {
    // ``doubly'' Linked List
    // Trick: elements of linked list will be themselves values in HashMap
    // This allows us to remove elements in O(1) as we would do in a linked list,
    // and at the same time look the elements up in O(1) as we would do in a HashMap.
    private static class DoubleNode<K, V> {
        public K key;
        public V value;
        public DoubleNode previous;
        public DoubleNode next;
        
        public DoubleNode(K key, V value) {
            this.value = value;
            this.key = key;
        }
    }
    
    // Doubly Linked List implementation based on DoubleNode
    // Will initialize with (and always keep) a placeholder fake head and tail
    // to simplify implementation, and reduce number of null checks
    private static class DoubleLinkedList<K, V> {
        private DoubleNode<K, V> head;
        private DoubleNode<K, V> tail;

        public DoubleNode<K, V> pop() {
            DoubleNode<K, V> tmp = this.head.next;
            this.head.next = tmp.next;
            tmp.next.previous = this.head;

            return tmp;
        }

        public void push(DoubleNode<K, V> node) {
            node.previous = this.tail.previous;
            node.next = this.tail;
            this.tail.previous = node;
            node.previous.next = node;
        }

        public void remove(DoubleNode<K, V> node) {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        
        public DoubleLinkedList() {
            this.head = new DoubleNode<K, V>(null, null);
            this.tail = new DoubleNode<K, V>(null, null);
            this.head.next = this.tail;
            this.tail.previous = this.head;
        }
    }

    
    // Actual LRU using linked list embedded inside a HashMap

    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */
    private int capacity;
    private int count;
    private HashMap<Integer, DoubleNode<Integer, Integer>> LRUCache;
    private DoubleLinkedList<Integer, Integer> LRUList;
    public LRUCache(int capacity) {
        this.count = 0;
        this.capacity = capacity;
        
        this.LRUCache = new HashMap<Integer, DoubleNode<Integer, Integer>>();
        this.LRUList = new DoubleLinkedList<Integer, Integer>();
    }
    
    public int get(int key) {
        DoubleNode<Integer, Integer> e = this.LRUCache.get(key);
        if (e == null) {
            return -1;
        }

        // Put e at the end to indicate it is the most recently used
        this.LRUList.remove(e);
        this.LRUList.push(e);
        return e.value;
    }
    
    public void put(int key, int value) {
        DoubleNode<Integer, Integer> old = this.LRUCache.get(key);
        if (old != null) {
            // Overwrite!
            old.value = value;
            this.LRUList.remove(old);
            this.LRUList.push(old);
        } else  if (this.count == this.capacity) {
            // LRU Cache is full, first evict!
            old = this.LRUList.pop();
            this.LRUCache.remove(old.key);

            // Reuse memory for new key
            old.key = key;
            old.value = value;
            this.LRUCache.put(key, old);
            this.LRUList.remove(old);
            this.LRUList.push(old);
        } else {
            // New with space remaining!
            DoubleNode<Integer, Integer> _new = new DoubleNode<Integer, Integer>(key, value);
            this.LRUCache.put(key, _new);
            this.LRUList.push(_new);
            this.count++;                
        }
    }
}