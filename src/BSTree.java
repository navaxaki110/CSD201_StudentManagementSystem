/**
 * Cây nhị phân tìm kiếm (Binary Search Tree - BST) để tối ưu hóa tra cứu sinh viên theo ID.
 * Cài đặt đầy đủ các thuật toán chuẩn theo Slide 4A-Trees1.ppt và 4B-Trees2.ppt:
 * - Chèn (Insert)
 * - Tìm kiếm nhị phân (Search theo ID - O(log n))
 * - Duyệt cây: In-order (tăng dần ID), Pre-order, Post-order
 * - Duyệt theo chiều rộng: Breadth-First Traversal (dùng Queue)
 * - Xóa Node: Delete by Copying (Xóa bằng sao chép)
 * - Đếm số Node và tính chiều cao cây (Height)
 */
public class BSTree {
    private TreeNode root;

    public BSTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public TreeNode getRoot() {
        return root;
    }

    /**
     * Chèn 1 sinh viên vào cây BST theo mã sinh viên (ID)
     * Trả về true nếu chèn thành công, false nếu ID đã tồn tại.
     */
    public boolean insert(Student student) {
        if (student == null) return false;
        TreeNode newNode = new TreeNode(student);

        if (isEmpty()) {
            root = newNode;
            return true;
        }

        TreeNode parent = null;
        TreeNode current = root;

        while (current != null) {
            int cmp = student.getId().compareToIgnoreCase(current.info.getId());
            if (cmp == 0) {
                // Trùng ID - không cho phép chèn trùng khóa
                return false;
            }
            parent = current;
            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        // Gắn vào cây con trái hoặc phải của parent
        if (student.getId().compareToIgnoreCase(parent.info.getId()) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        return true;
    }

    /**
     * Tìm kiếm sinh viên theo ID trên cây BST.
     * Độ phức tạp trung bình O(log n), tốt hơn nhiều so với O(n) của danh sách liên kết.
     */
    public Student search(String id) {
        TreeNode current = root;
        while (current != null) {
            int cmp = id.compareToIgnoreCase(current.info.getId());
            if (cmp == 0) {
                return current.info;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    /**
     * Tìm kiếm sinh viên và đếm số bước so sánh (dùng minh họa thuyết trình trực quan)
     */
    public SearchResult searchWithStats(String id) {
        int steps = 0;
        TreeNode current = root;
        while (current != null) {
            steps++;
            int cmp = id.compareToIgnoreCase(current.info.getId());
            if (cmp == 0) {
                return new SearchResult(current.info, steps, true);
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return new SearchResult(null, steps, false);
    }

    public static class SearchResult {
        public Student student;
        public int steps;
        public boolean found;

        public SearchResult(Student student, int steps, boolean found) {
            this.student = student;
            this.steps = steps;
            this.found = found;
        }
    }

    /**
     * Duyệt trung thứ tự In-Order (Left - Root - Right):
     * Tính chất đặc biệt: Danh sách sinh viên sẽ được in theo thứ tự ID TĂNG DẦN (A -> Z).
     */
    public void inOrder() {
        if (isEmpty()) {
            System.out.println("(!) Cay BST hien dang trong.");
            return;
        }
        MyList.printTableHeader();
        inOrderRec(root);
        MyList.printTableFooter();
    }

    private void inOrderRec(TreeNode p) {
        if (p == null) return;
        inOrderRec(p.left);
        System.out.println(p.info);
        inOrderRec(p.right);
    }

    /**
     * Duyệt tiền thứ tự Pre-Order (Root - Left - Right)
     */
    public void preOrder() {
        if (isEmpty()) {
            System.out.println("(!) Cay BST hien dang trong.");
            return;
        }
        MyList.printTableHeader();
        preOrderRec(root);
        MyList.printTableFooter();
    }

    private void preOrderRec(TreeNode p) {
        if (p == null) return;
        System.out.println(p.info);
        preOrderRec(p.left);
        preOrderRec(p.right);
    }

    /**
     * Duyệt hậu thứ tự Post-Order (Left - Right - Root)
     */
    public void postOrder() {
        if (isEmpty()) {
            System.out.println("(!) Cay BST hien dang trong.");
            return;
        }
        MyList.printTableHeader();
        postOrderRec(root);
        MyList.printTableFooter();
    }

    private void postOrderRec(TreeNode p) {
        if (p == null) return;
        postOrderRec(p.left);
        postOrderRec(p.right);
        System.out.println(p.info);
    }

    /**
     * Duyệt theo chiều rộng (Breadth-First Traversal / Level-Order):
     * Sử dụng cấu trúc hàng đợi Queue để duyệt từng tầng của cây từ trên xuống dưới.
     */
    public void breadthFirst() {
        if (isEmpty()) {
            System.out.println("(!) Cay BST hien dang trong.");
            return;
        }
        MyList.printTableHeader();
        TreeQueue queue = new TreeQueue();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            TreeNode p = queue.dequeue();
            System.out.println(p.info);
            if (p.left != null) queue.enqueue(p.left);
            if (p.right != null) queue.enqueue(p.right);
        }
        MyList.printTableFooter();
    }

    /**
     * Xóa 1 Node khỏi cây BST bằng phương pháp sao chép (Delete by Copying)
     * Đúng chuẩn thuật toán trong Slide 4B-Trees2.ppt.
     */
    public boolean deleteByCopying(String id) {
        TreeNode parent = null;
        TreeNode current = root;

        // 1. Tìm node cần xóa (current) và node cha của nó (parent)
        while (current != null) {
            int cmp = id.compareToIgnoreCase(current.info.getId());
            if (cmp == 0) break;
            parent = current;
            if (cmp < 0) current = current.left;
            else current = current.right;
        }

        if (current == null) return false; // Không tìm thấy ID để xóa

        // Trường hợp 1 & 2: current có tối đa 1 nhánh con
        if (current.left == null || current.right == null) {
            TreeNode child = (current.left != null) ? current.left : current.right;

            if (parent == null) {
                // Node cần xóa là root
                root = child;
            } else if (parent.left == current) {
                parent.left = child;
            } else {
                parent.right = child;
            }
            return true;
        }

        // Trường hợp 3: current có cả 2 nhánh con (trái và phải)
        // Tìm node cực phải của cây con bên trái (predecessor - phần tử lớn nhất bên cây con trái)
        TreeNode pR = current.left;
        TreeNode parentOfPR = current;

        while (pR.right != null) {
            parentOfPR = pR;
            pR = pR.right;
        }

        // Sao chép dữ liệu từ pR sang current
        current.info = pR.info;

        // Xóa node pR ban đầu
        if (parentOfPR == current) {
            parentOfPR.left = pR.left;
        } else {
            parentOfPR.right = pR.left;
        }

        return true;
    }

    /**
     * Đếm tổng số Node trên cây
     */
    public int count() {
        return countRec(root);
    }

    private int countRec(TreeNode p) {
        if (p == null) return 0;
        return 1 + countRec(p.left) + countRec(p.right);
    }

    /**
     * Tính chiều cao của cây (Height)
     */
    public int height() {
        return heightRec(root);
    }

    private int heightRec(TreeNode p) {
        if (p == null) return 0;
        int leftH = heightRec(p.left);
        int rightH = heightRec(p.right);
        return 1 + Math.max(leftH, rightH);
    }
}

