# Week 4

## Priority Queues

### Exercise

Event-driven simulation:

1. [CollisionSystem.java](https://github.com/seineo/Algorithms/blob/master/Part1/week4/CollisionSystem.java)
2. [Particle.java](https://github.com/seineo/Algorithms/blob/master/Part1/week4/Particle.java)

Reference source files and input files are [here](https://algs4.cs.princeton.edu/61event/).

### Assignment

8 Puzzle:

1. [Board.java](https://github.com/seineo/Algorithms/blob/master/Part1/week4/Board.java)
2. [Solver.java](https://github.com/seineo/Algorithms/blob/master/Part1/week4/Solver.java)

### Interview Questions

1. [Dynamic median](https://github.com/seineo/Algorithms/blob/master/Part1/week4/DynamicMedian.java)

2. [Randamized priority queue](https://github.com/seineo/Algorithms/blob/master/Part1/week4/RandamizedHeap.java)

3. [Taxicab numbers](https://github.com/seineo/Algorithms/blob/master/Part1/week4/TaxicabNumbers.java)

   [Reference](https://github.com/keweishang/algorithm_princeton/blob/master/interview_questions/questions_answers_7.md)

## Elementary Symbol Tables

### Interview Questions

1. Java autoboxing and equals():

   ```java
   // 0.0 and -0.0
   double a = 0.0, b = -0.0;
   Double x = new Double(a);
   Double y = new Double(b);
   StdOut.println(a == b);   // true
   StdOut.println(x.equals(y));  // false
   
   // NaN and NaN
   double a = NaN, b = NaN;
   Double x = new Double(a);
   Double y = new Double(b);
   StdOut.println(a == b);   // false
   StdOut.println(x.equals(y));  // true
   ```

2. Check if a binary tree is a BST:

   ```java
   private boolean isBST(Node root) {
       return isBST(root, null, null);
   }
   
   // if min or max is null, it means there's no restrains for this BST
   private boolean isBST(Node root, Key min, Key max) {
       if (root == null)
           return true;
       if (min != null && root.key.compareTo(min) < 0)
           return false;
       else if (max != null && root.key.compareTo(max) > 0)
           return false;
       else
           return isBST(root.left, min, root.key) && isBST(root.right, root.key, max);
   }
   ```

3. Inorder traversal with constant extra space:

   ```java
   // morris traversal
   private void inorder(Node root, Queue<Key> q) {
       while (root != null) {
           if (root.left == null) {
               q.enqueue(root.key);
               root = root.right;
           } else {
               Node pre = root.left;
               while (pre.right != null && pre.right != root)
                   pre = pre.right;
               if (pre.right == null) {
                   pre.right = root;
                   root = root.left;
               } else {
                   pre.right = null;
                   q.enqueue(root.key);
                   root = root.right;
               }
           }
       }
   }
   ```

4. Web tracking:

   maintain a symbol table of symbol tables