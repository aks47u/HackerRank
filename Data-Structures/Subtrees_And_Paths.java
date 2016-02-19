import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
 
public class Subtrees_And_Paths {
    private static class Node {
        Node l = null, r = null, p = null;
        boolean root = true;
        long value, sumValues, maxValue;
        int ind;
 
        Node(int ind) {
            this.ind = ind;
        }
 
        public String toString() {
            String ans = ind + ": [";
            ans += "value = " + value;
            ans += ", sumValues = " + sumValues;
            ans += ", maxValue = " + maxValue;
            ans += ", p = " + (p == null ? "null" : p.ind);
            ans += ", l = " + (l == null ? "null" : l.ind);
            ans += ", r = " + (r == null ? "null" : r.ind);
 
            if (root) {
                ans += ", root";
            }
 
            ans = ans + "]";
 
            return ans;
        }
    }
 
    private static void fixUp(Node n) {
        n.maxValue = n.sumValues = n.value;
 
        if (n.l != null) {
            n.sumValues += n.l.sumValues;
            n.maxValue = Math.max(n.maxValue, n.l.maxValue + n.value);
        }
 
        if (n.r != null) {
            n.maxValue += n.r.sumValues;
            n.sumValues += n.r.sumValues;
            n.maxValue = Math.max(n.maxValue, n.r.maxValue);
        }
    }
 
    @Deprecated
    private static void fixDown(Node n) {
    }
 
    private static void rotate(Node x) {
        Node y = x.p;
        x.p = y.p;
 
        if (y.root) {
            y.root = false;
            x.root = true;
        } else if (y == y.p.l) {
            y.p.l = x;
        } else {
            y.p.r = x;
        }
 
        y.p = x;
 
        if (x == y.l) {
            y.l = x.r;
 
            if (y.l != null) {
                y.l.p = y;
            }
 
            x.r = y;
        } else {
            y.r = x.l;
 
            if (y.r != null) {
                y.r.p = y;
            }
 
            x.l = y;
        }
 
        fixUp(y);
        fixUp(x);
    }
 
    private static void splay(Node x) {
        while (!x.root) {
            if (!x.p.root) {
                fixDown(x.p.p);
            }
 
            fixDown(x.p);
            fixDown(x);
 
            if (x.p.root) {
                rotate(x);
            } else {
                if (x == x.p.l && x.p == x.p.p.l || x == x.p.r
                        && x.p == x.p.p.r) {
                    rotate(x.p);
                    rotate(x);
                } else {
                    rotate(x);
                    rotate(x);
                }
            }
        }
    }
 
    private static Node join(Node l, Node x, Node r) {
        x.l = l;
        x.r = r;
 
        if (l != null) {
            l.p = x;
            l.root = false;
        }
 
        if (r != null) {
            r.p = x;
            r.root = false;
        }
 
        fixUp(x);
 
        return x;
    }
 
    private static Node q, r;
 
    private static void split(Node x) {
        splay(x);
        fixDown(x);
 
        if (x.l != null) {
            x.l.root = true;
            x.l.p = null;
        }
 
        if (x.r != null) {
            x.r.root = true;
            x.r.p = null;
        }
 
        x.p = null;
        q = x.l;
        r = x.r;
        x.l = x.r = null;
        fixUp(x);
    }
 
    private static Node findPath(Node x) {
        splay(x);
 
        return x;
    }
 
    private static void link(Node x, Node y) {
        join(null, expose(x), expose(y)).p = null;
    }
 
    private static void unlink(Node x) {
        expose(x);
        splay(x);
        fixDown(x);
 
        if (x.r != null) {
            x.r.p = null;
            x.r.root = true;
        }
 
        x.r = null;
        fixUp(x);
    }
 
    private static Node expose(Node v) {
        Node p = null;
 
        while (v != null) {
            Node w = findPath(v).p;
            split(v);
 
            if (q != null) {
                q.p = v;
            }
 
            p = join(p, v, r);
            v = w;
        }
 
        p.p = null;
 
        return p;
    }
 
    @SuppressWarnings("unused")
    private static void solve(Input in, PrintWriter out) throws IOException {
        int n = in.nextInt();
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] edges = new ArrayList[n];
 
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
 
        for (int i = 0; i < n - 1; ++i) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            edges[u].add(v);
            edges[v].add(u);
        }
 
        Node[] ns = new Node[n];
        linkDfs(0, -1, edges, ns);
        int qs = in.nextInt();
 
        for (int it = 0; it < qs; ++it) {
            if ("add".equals(in.next())) {
                Node node = ns[in.nextInt() - 1];
                splay(node);
                node.value += in.nextLong();
                fixUp(node);
            } else {
                Node u = ns[in.nextInt() - 1];
                Node v = ns[in.nextInt() - 1];
                expose(u);
                expose(v);
                splay(u);
                Node lca = u.p;
                long ans;
 
                if (lca == null) {
                    lca = u;
                }
 
                splay(lca);
                ans = lca.value + (lca.r == null ? 0 : lca.r.sumValues);
 
                if (lca.l != null) {
                    ans = Math.max(ans, lca.l.maxValue + lca.value
                            + (lca.r == null ? 0 : lca.r.sumValues));
                }
 
                expose(u);
 
                if (lca == null) {
                    lca = u;
                }
 
                splay(lca);
                ans = Math.max(ans, lca.value
                        + (lca.r == null ? 0 : lca.r.sumValues));
 
                if (lca.l != null) {
                    ans = Math.max(ans, lca.l.maxValue + lca.value
                            + (lca.r == null ? 0 : lca.r.sumValues));
                }
 
                out.println(ans);
                out.flush();
            }
        }
    }
 
    private static Node linkDfs(int i, int p, ArrayList<Integer>[] edges,
            Node[] ns) {
        ns[i] = new Node(i);
 
        for (int j : edges[i]) {
            if (j != p) {
                link(linkDfs(j, i, edges, ns), ns[i]);
            }
        }
 
        return ns[i];
    }
 
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        solve(new Input(new BufferedReader(new InputStreamReader(System.in))),
                out);
        out.close();
    }
 
    private static class Input {
        BufferedReader in;
        StringBuilder sb = new StringBuilder();
 
        public Input(BufferedReader in) {
            this.in = in;
        }
 
        public Input(String s) {
            this.in = new BufferedReader(new StringReader(s));
        }
 
        public String next() throws IOException {
            sb.setLength(0);
 
            while (true) {
                int c = in.read();
 
                if (c == -1) {
                    return null;
                }
 
                if (" \n\r\t".indexOf(c) == -1) {
                    sb.append((char) c);
                    break;
                }
            }
 
            while (true) {
                int c = in.read();
 
                if (c == -1 || " \n\r\t".indexOf(c) != -1) {
                    break;
                }
 
                sb.append((char) c);
            }
 
            return sb.toString();
        }
 
        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
 
        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
 
        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
    }
}
