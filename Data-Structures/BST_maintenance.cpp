#include <cstdio>
#include <iostream>
#include <vector>

using namespace std;

#define maxn 1000005

struct tree_node {
	int l, r, tag;
}

tree[maxn];

struct info {
	int cut_vertex, cur_dist, subtree_idx;

	info() {
	}

	info(int _cut_vertex, int _cur_dist, int _subtree_idx) {
		cut_vertex = _cut_vertex;
		cur_dist = _cur_dist;
		subtree_idx = _subtree_idx;
	}
}

cur;

vector<info> belongs[maxn];
vector<int> num_of_subtree_added[maxn];
vector<long long> sum_of_subtree_distances[maxn];
int  was[maxn], iteration, sn_sz, sub_nodes[maxn], subtree[maxn], q, f[maxn],
		t[maxn], p[maxn], n, i, anc, tn, ii, _cut_vertex, j, dst[maxn],
		queue[maxn], q1, q2, sp, st[maxn], Last[maxn], a[maxn], bst_left[maxn],
		bst_right[maxn];
long long sum_of_distances[maxn], num_of_part_vertexes[maxn], ret;
bool cut[maxn];

void addedge(int x, int y) {
	t[++ii] = y;
	p[ii] = f[x];
	f[x] = ii;
}

int dfs(int k) {
	st[sp = 1] = k;

	while (sp > 0) {
		k = st[sp];

		if (was[k] != iteration) {
			was[k] = iteration;
			sub_nodes[++sn_sz] = k;
			subtree[k] = 1;
			Last[k] = f[k];
		}

		while (Last[k] > 0 && (was[t[Last[k]]] == iteration || cut[t[Last[k]]])) {
			Last[k] = p[Last[k]];
		}

		if (Last[k] > 0) {
			st[++sp] = t[Last[k]];
			Last[k] = p[Last[k]];
			continue;
		}

		if (sp > 1) {
			subtree[st[sp - 1]] += subtree[k];
		}

		--sp;
	}

	return subtree[st[1]];
}

void bfs(int k, int cur_dist, int sub_idx) {
	q1 = q2 = 0;
	queue[q1++] = k;
	dst[k] = cur_dist;
	was[k] = iteration;
	belongs[k].push_back(info(_cut_vertex, cur_dist, sub_idx));

	while (q1 != q2) {
		k = queue[q2++];
		cur_dist = dst[k];
		int q = f[k];

		while (q > 0) {
			if (was[t[q]] != iteration && !cut[t[q]]) {
				was[t[q]] = iteration;
				dst[t[q]] = cur_dist + 1;
				queue[q1++] = t[q];
				belongs[t[q]].push_back(
						info(_cut_vertex, cur_dist + 1, sub_idx));
			}

			q = p[q];
		}
	}
}

void collect_info(int cut_vertex) {
	belongs[cut_vertex].push_back(info(cut_vertex, 0, -1));
	_cut_vertex = cut_vertex;
	sum_of_distances[cut_vertex] = 0;
	int q = f[cut_vertex];

	while (q > 0) {
		if (!cut[t[q]]) {
			was[cut_vertex] = ++iteration;
			sum_of_subtree_distances[cut_vertex].push_back(0);
			num_of_subtree_added[cut_vertex].push_back(0);
			bfs(t[q], 1, sum_of_subtree_distances[cut_vertex].size() - 1);
		}

		q = p[q];
	}
}

void centroid_build(int k) {
	sn_sz = 0;
	++iteration;

	if (dfs(k) == 1) {
		cut[k] = true;

		return;
	}

	int optimal = n + 1, q, cut_vertex, max_subtree;

	for (i = 1; i <= sn_sz; i++) {
		tn = sub_nodes[i];
		max_subtree = 0;
		q = f[tn];

		while (q > 0) {
			if (was[t[q]] == iteration && subtree[tn] > subtree[t[q]]) {
				max_subtree = max(max_subtree, subtree[t[q]]);
			}

			q = p[q];
		}

		max_subtree = max(max_subtree, sn_sz - subtree[tn]);

		if (max_subtree < optimal) {
			optimal = max_subtree;
			cut_vertex = tn;
		}
	}

	collect_info(cut_vertex);
	cut[cut_vertex] = true;
	q = f[cut_vertex];

	while (q > 0) {
		if (!cut[t[q]]) {
			centroid_build(t[q]);
		}

		q = p[q];
	}
}

void add_vertex(int k) {
	for (j = 0; j < belongs[k].size(); j++) {
		cur = belongs[k][j];
		++num_of_part_vertexes[cur.cut_vertex];

		if (cur.cut_vertex != k) {
			++num_of_subtree_added[cur.cut_vertex][cur.subtree_idx];
			sum_of_subtree_distances[cur.cut_vertex][cur.subtree_idx] +=
					cur.cur_dist;
			sum_of_distances[cur.cut_vertex] += cur.cur_dist;
			ret +=
					cur.cur_dist
							* (num_of_part_vertexes[cur.cut_vertex]
									- num_of_subtree_added[cur.cut_vertex][cur.subtree_idx]);
			ret += sum_of_distances[cur.cut_vertex]
					- sum_of_subtree_distances[cur.cut_vertex][cur.subtree_idx];
		} else {
			ret += sum_of_distances[cur.cut_vertex];
		}
	}
}

void init(int pos, int l, int r) {
	tree[pos].l = l, tree[pos].r = r;

	if (l < r) {
		init(pos + pos, l, (l + r) / 2);
		init(pos + pos + 1, (l + r) / 2 + 1, r);
	}
}

void modify(int pos, int l, int r, int j) {
	if (tree[pos].l == l && tree[pos].r == r) {
		tree[pos].tag = j;
	} else {
		if (l <= min(r, tree[pos + pos].r)) {
			modify(pos + pos, l, min(r, tree[pos + pos].r), j);
		}

		if (max(l, tree[pos + pos + 1].l) <= r) {
			modify(pos + pos + 1, max(l, tree[pos + pos + 1].l), r, j);
		}
	}
}

int get_node(int pos, int j) {
	if (tree[pos].l == tree[pos].r) {
		return tree[pos].tag;
	} else {
		if (j <= tree[pos + pos].r) {
			return max(tree[pos].tag, get_node(pos + pos, j));
		} else {
			return max(tree[pos].tag, get_node(pos + pos + 1, j));
		}
	}
}

int main(int argc, char * const argv[]) {
	scanf("%d", &n);

	for (i = 1; i <= n; i++) {
		scanf("%d", &a[i]);
	}

	init(1, 1, n);
	modify(1, 1, n, 1);
	bst_left[1] = 1, bst_right[1] = n;

	for (i = 2; i <= n; i++) {
		anc = get_node(1, a[i]);
		addedge(anc, i);
		addedge(i, anc);

		if (a[anc] > a[i]) {
			bst_left[i] = bst_left[anc];
			bst_right[i] = a[anc] - 1;
		} else {
			bst_left[i] = a[anc] + 1;
			bst_right[i] = bst_right[anc];
		}

		if (bst_left[i] <= bst_right[i]) {
			modify(1, bst_left[i], bst_right[i], i);
		}
	}

	centroid_build(1);

	for (i = 1; i <= n; i++) {
		add_vertex(i);
		printf("%lld\n", ret);
	}

	return 0;
}
