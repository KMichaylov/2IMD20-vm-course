//	Creating objects via strings and logic for introspecting these objects.

function CreateContext()
{
	context = new();
	context.elements = 0;
	return context;
}

function RegisterMember(context, name, total_arguments, arguments)
{
	context[context.elements] = new();
	context[context.elements].name = name;
	context[context.elements].total_arguments = total_arguments;
	context[context.elements].arguments = arguments;

	context.elements = context.elements + 1;
}

function CreateVariable(context, name)
{
	eval("sl", "function CreateVariable_(context) { context." + name + " = null; }");
	CreateVariable_(context);
	RegisterMember(context, name, 0, "");
}

function CreateConstantFunction(context, name, code)
{
	eval("sl", "function _" + name + "() { " + code + " }");
	eval("sl", "function CreateConstantFunction_(context) { context." + name + " = _" + name + "; }");
	CreateConstantFunction_(context);
	RegisterMember(context, name, 0, "");
}

function CreateFunction(context, name, total_arguments, arguments, code)
{
	eval("sl", "function _" + name + "(" + arguments + ") { " + code + " }");
	eval("sl", "function CreateFunction_(context) { context." + name + " = _" + name + "; }");
	CreateFunction_(context);
	RegisterMember(context, name, total_arguments, arguments);
}

function ListVariables(context)
{
	i = 0;
	while (i < context.elements)
	{
		eval("sl", "function ListVariable_(context) { return context." + context[i].name + "; }");
		if (context[i].total_arguments != 0)
		{
			println(context[i].name + ": " + ListVariable_(context) + "[" + context[i].total_arguments + "](" + context[i].arguments + ")");
		}
		else
		{
			println(context[i].name + ": " + ListVariable_(context) + "[" + context[i].total_arguments + "]");
		}
		i = i + 1;
	}
}

function main()
{
	context = CreateContext();
	CreateVariable(context, "i");
	CreateVariable(context, "j");
	CreateVariable(context, "k");
	context.i = 10;
	context.j = 20;
	context.k = 40;

	CreateConstantFunction(context, "return_one", "return 1;");
	CreateConstantFunction(context, "return_two", "return 2;");
	CreateFunction(context, "add_two_numbers", 2, "a, b", "return a + b;");

	println(context.return_one());
	println(context.return_two());
	println(context.add_two_numbers(17, 31));

	ListVariables(context);
}
