//	Binary tree.

function CreateBinaryTree()
{
	top = new();
	top.value = null;
	top.left = null;
	top.right = null;
	return top;
}

function Register(top, value) {
	if (top.value == null)
	{
		top.value = value;
		return;
	}
	
	if (top.value == value)
	{
		// Already present
		return;
	}
	
	// Current node is not matching, check lower elements
	if (value < top.value)
	{
		// Check left side
		if (top.left == null)
		{
			top.left = CreateBinaryTree();
			top.left.value = value;
			return;
		}
		
		return Register(top.left, value);
	}
	else
	{
		// Check right side
		if (top.right == null)
		{
			top.right = CreateBinaryTree();
			top.right.value = value;
			return;
		}
		
		return Register(top.right, value);
	}
}

function FormatTree(tree) {
	result = "";
	if (tree.value != null)
	{
		result = result + tree.value;
	}
	
	result = result + "[";
	if (tree.left != null)
	{
		result = result + FormatTree(tree.left);
	}
	result = result + ", ";
	
	if (tree.right != null)
	{
		result = result + FormatTree(tree.right);
	}
	result = result + "]";
	
	return result;
}

function main() {
	tree = CreateBinaryTree();
	max = 400;
	i = 0;
	Register(tree, max / 2);
	while (i < max)
	{
		Register(tree, i);
		i = i + 1;
	}	
		
	println(FormatTree(tree));
}