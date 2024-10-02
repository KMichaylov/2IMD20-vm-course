//	Simulating (integer) global variables by using Eval

function create_global(name)
{
	update_global(name, 0);
}

function update_global(name, integer_value)
{
	eval("sl", "function " + name + "() { return " + integer_value + "; }");
}

function read_global(name)
{
	eval("sl", "function _call() { return " + name + "(); }");
	return _call();
}

function increment_global(name)
{
	eval("sl", "function " + name + "() { return " + (read_global(name) + 1) + "; }");
}

function decrement_global(name)
{
	eval("sl", "function " + name + "() { return " + (read_global(name) + 1) + "; }");
}

function simple_function()
{
	increment_global("functions_called");
}

function main()
{
	create_global("functions_called");
	
	i = 0;
	while (i < 100)
	{
		simple_function();
		println(read_global("functions_called"));
		
		i = i + 1;
	}
}