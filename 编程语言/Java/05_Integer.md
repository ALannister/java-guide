~~~
public class IntegerTest {
	public static void main(String[] args) {
		//Integer变量直接赋值的情形，
		//-128~127范围是直接从缓存里拿的引用，值相等取的是同一个引用
		//其余范围的需要new一个对象，所以引用肯定不相等
		for(int i = -130; i < 130; i++) {
			Integer a = i;
			Integer b = i;
			System.out.println(i + ": " + (a==b));
		}
		//Integer变量通过new产生的，引用自然不同
		Integer a1 = new Integer(1);
		Integer b1 = new Integer(1);
		System.out.println(a1 == b1);
	}
}
~~~


~~~
-130: false
-129: false
-128: true
-127: true
-126: true
-125: true
-124: true
-123: true
-122: true
-121: true
-120: true
-119: true
-118: true
-117: true
-116: true
-115: true
-114: true
-113: true
-112: true
-111: true
-110: true
-109: true
-108: true
-107: true
-106: true
-105: true
-104: true
-103: true
-102: true
-101: true
-100: true
-99: true
-98: true
-97: true
-96: true
-95: true
-94: true
-93: true
-92: true
-91: true
-90: true
-89: true
-88: true
-87: true
-86: true
-85: true
-84: true
-83: true
-82: true
-81: true
-80: true
-79: true
-78: true
-77: true
-76: true
-75: true
-74: true
-73: true
-72: true
-71: true
-70: true
-69: true
-68: true
-67: true
-66: true
-65: true
-64: true
-63: true
-62: true
-61: true
-60: true
-59: true
-58: true
-57: true
-56: true
-55: true
-54: true
-53: true
-52: true
-51: true
-50: true
-49: true
-48: true
-47: true
-46: true
-45: true
-44: true
-43: true
-42: true
-41: true
-40: true
-39: true
-38: true
-37: true
-36: true
-35: true
-34: true
-33: true
-32: true
-31: true
-30: true
-29: true
-28: true
-27: true
-26: true
-25: true
-24: true
-23: true
-22: true
-21: true
-20: true
-19: true
-18: true
-17: true
-16: true
-15: true
-14: true
-13: true
-12: true
-11: true
-10: true
-9: true
-8: true
-7: true
-6: true
-5: true
-4: true
-3: true
-2: true
-1: true
0: true
1: true
2: true
3: true
4: true
5: true
6: true
7: true
8: true
9: true
10: true
11: true
12: true
13: true
14: true
15: true
16: true
17: true
18: true
19: true
20: true
21: true
22: true
23: true
24: true
25: true
26: true
27: true
28: true
29: true
30: true
31: true
32: true
33: true
34: true
35: true
36: true
37: true
38: true
39: true
40: true
41: true
42: true
43: true
44: true
45: true
46: true
47: true
48: true
49: true
50: true
51: true
52: true
53: true
54: true
55: true
56: true
57: true
58: true
59: true
60: true
61: true
62: true
63: true
64: true
65: true
66: true
67: true
68: true
69: true
70: true
71: true
72: true
73: true
74: true
75: true
76: true
77: true
78: true
79: true
80: true
81: true
82: true
83: true
84: true
85: true
86: true
87: true
88: true
89: true
90: true
91: true
92: true
93: true
94: true
95: true
96: true
97: true
98: true
99: true
100: true
101: true
102: true
103: true
104: true
105: true
106: true
107: true
108: true
109: true
110: true
111: true
112: true
113: true
114: true
115: true
116: true
117: true
118: true
119: true
120: true
121: true
122: true
123: true
124: true
125: true
126: true
127: true
128: false
129: false
false
~~~

