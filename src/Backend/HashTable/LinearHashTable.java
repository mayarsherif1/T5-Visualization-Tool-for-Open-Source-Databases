package Backend.HashTable;

public class LinearHashTable {
    private Integer[] hashTable;
    private int capacity;
    private int size =0;
    private double maxUtilization;

    public LinearHashTable(int capacity, double maxUtilization) {
        this.capacity = capacity;
        hashTable = new Integer[capacity];
        this.maxUtilization = maxUtilization;
    }

    private int hash(int key) {
        return key % capacity;
    }

    public void insert(String binaryKey) {
        if (getUtilization() >= maxUtilization) {
            resize();
        }
        int key = Integer.parseInt(binaryKey, 2);
        int index = hash(key);
        int startIndex = index;
        while (hashTable[index] != null) {
            System.out.println("Collision at " + index + "; trying next index.");
            index = (index + 1) % capacity;
            if (index == startIndex) {
                System.out.println("Hash table is full. Unable to insert key: " + key);
                return;
            }
        }
        hashTable[index] = key;
        size++;
        System.out.println("Inserted key " + key + " at index " + index);
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Integer[] newTable = new Integer[newCapacity];
        Integer[] oldTable = hashTable;
        hashTable = newTable;
        capacity = newCapacity;
        size = 0;

        for (Integer key : oldTable) {
            if (key != null) {
                insert(Integer.toBinaryString(key));
            }
        }
    }


    public boolean find(int key) {
        int index = hash(key);
        while (hashTable[index] != null) {
            if (hashTable[index] == key) {
                return true;
            }
            index = (index + 1) % capacity;
            if (index == hash(key)) { // If we return to the starting index
                break;
            }
        }
        return false;
    }

    // Method to delete a key from the hash table
    public void delete(int key) {
        int index = hash(key);
        while (hashTable[index] != null) {
            if (hashTable[index] == key) {
                hashTable[index] = null; // Set the slot to null to delete the key
                rehash(index);
                return;
            }
            index = (index + 1) % capacity;
            if (index == hash(key)) { // If we return to the starting index
                break;
            }
        }
        size--;
        System.out.println("Key not found: " + key);
    }

    // Rehash the keys in the case of a deletion
    private void rehash(int start) {
        int index = (start + 1) % capacity;
        int startIndex = index;
        while (hashTable[index] != null) {
            Integer keyToRehash = hashTable[index];
            System.out.println("Rehashing key " + keyToRehash + " from index " + index);
            hashTable[index] = null;
            insert(Integer.toBinaryString(keyToRehash));
            index = (index + 1) % capacity;
            if (index == startIndex) {
                System.out.println("Completed one full cycle. Breaking rehash.");
                break;
            }
        }
    }

    // Method to display the hash table
    public void displayTable() {
        System.out.println("Hash Table: ");
        for (int i = 0; i < capacity; i++) {
            if (hashTable[i] == null) {
                System.out.println(i + ": null");
            } else {
                System.out.println(i + ": " + hashTable[i]);
            }
        }
    }
    public double getUtilization() {
        return (double) size / capacity;
    }


    public Integer[] getTable() {
        return hashTable;
    }

    public int getCapacity() {
        return capacity;
    }
}
