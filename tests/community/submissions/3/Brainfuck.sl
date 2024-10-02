//
//	Brainfuck Interpreter + Hello World
//
//	Implementation contains 7 of the 8 operators and supports a partial character map.
//	SL does not support every character and does not support input.
//
//	Printing is done after the program has been executed.
//
//	Made by: Thimo Böhmer
//

function Brainfuck_CreateProgram_HelloWorld()
{
	// Brainfuck script:
	// ++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.
	// From: "brainfuck". esolangs.org (https://esolangs.org/wiki/Brainfuck). Retrieved 19-09-2024
	
	i = 0;
	code = new();
	
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "[";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "[";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "<";
	i = i + 1;
	code[i] = "<";
	i = i + 1;
	code[i] = "<";
	i = i + 1;
	code[i] = "<";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "]";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "[";
	i = i + 1;
	code[i] = "<";
	i = i + 1;
	code[i] = "]";
	i = i + 1;
	code[i] = "<";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "]";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = "<";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = "<";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = "-";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	code[i] = ">";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = "+";
	i = i + 1;
	code[i] = ".";
	i = i + 1;
	
	code.size = i;
	return code;
}

function Brainfuck_InitializeMemory(size)
{
	data = new();
	i = 0;
	while (i < size)
	{
		data[i] = 0;
		i = i + 1;
	}
	
	data.size = size;
	return data;
}

function Brainfuck_Format(value)
{
	if (value == 32)
	{
		return " ";
	}
	if (value == 33)
	{
		return "!";
	}
	if (value == 65)
	{
		return "A";
	}
	if (value == 66)
	{
		return "B";
	}
	if (value == 67)
	{
		return "C";
	}
	if (value == 68)
	{
		return "D";
	}
	if (value == 69)
	{
		return "E";
	}
	if (value == 70)
	{
		return "F";
	}
	if (value == 71)
	{
		return "G";
	}
	if (value == 72)
	{
		return "H";
	}
	if (value == 73)
	{
		return "I";
	}
	if (value == 74)
	{
		return "J";
	}
	if (value == 75)
	{
		return "K";
	}
	if (value == 76)
	{
		return "L";
	}
	if (value == 77)
	{
		return "M";
	}
	if (value == 78)
	{
		return "N";
	}
	if (value == 79)
	{
		return "O";
	}
	if (value == 80)
	{
		return "P";
	}
	if (value == 81)
	{
		return "Q";
	}
	if (value == 82)
	{
		return "R";
	}
	if (value == 83)
	{
		return "S";
	}
	if (value == 84)
	{
		return "T";
	}
	if (value == 85)
	{
		return "U";
	}
	if (value == 86)
	{
		return "V";
	}
	if (value == 87)
	{
		return "W";
	}
	if (value == 88)
	{
		return "X";
	}
	if (value == 89)
	{
		return "Y";
	}
	if (value == 90)
	{
		return "Z";
	}
	if (value == 97)
	{
		return "a";
	}
	if (value == 98)
	{
		return "b";
	}
	if (value == 99)
	{
		return "c";
	}
	if (value == 100)
	{
		return "d";
	}
	if (value == 101)
	{
		return "e";
	}
	if (value == 102)
	{
		return "f";
	}
	if (value == 103)
	{
		return "g";
	}
	if (value == 104)
	{
		return "h";
	}
	if (value == 105)
	{
		return "i";
	}
	if (value == 106)
	{
		return "j";
	}
	if (value == 107)
	{
		return "k";
	}
	if (value == 108)
	{
		return "l";
	}
	if (value == 109)
	{
		return "m";
	}
	if (value == 110)
	{
		return "n";
	}
	if (value == 111)
	{
		return "o";
	}
	if (value == 112)
	{
		return "p";
	}
	if (value == 113)
	{
		return "q";
	}
	if (value == 114)
	{
		return "r";
	}
	if (value == 115)
	{
		return "s";
	}
	if (value == 116)
	{
		return "t";
	}
	if (value == 117)
	{
		return "u";
	}
	if (value == 118)
	{
		return "v";
	}
	if (value == 119)
	{
		return "w";
	}
	if (value == 120)
	{
		return "x";
	}
	if (value == 121)
	{
		return "y";
	}
	if (value == 122)
	{
		return "z";
	}
	if (value == 123)
	{
		return "{";
	}
	if (value == 124)
	{
		return "|";
	}
	if (value == 125)
	{
		return "}";
	}
	if (value == 126)
	{
		return "~";
	}
	
	// Unmapped character
	// SL is limited in its support of character literals so not every character can be formatted.
	return "";
}

function Brainfuck_Interpret(data, code)
{
	instruction_pointer = 0;
	data_pointer = 0;
	output = "";
	
	while (instruction_pointer < code.size)
	{
		if (code[instruction_pointer] == ">")
		{
			data_pointer = data_pointer + 1;
		}
		if (code[instruction_pointer] == "<")
		{
			data_pointer = data_pointer - 1;
		}
		if (code[instruction_pointer] == "+")
		{
			data[data_pointer] = data[data_pointer] + 1;
		}
		if (code[instruction_pointer] == "-")
		{
			data[data_pointer] = data[data_pointer] - 1;
		}
		if (code[instruction_pointer] == ".")
		{
			output = output + Brainfuck_Format(data[data_pointer]);
		}
		if (code[instruction_pointer] == "[")
		{
			if (data[data_pointer] == 0)
			{
				depth = 0;
				while (1 == 1)
				{
					instruction_pointer = instruction_pointer + 1;
					
					if (code[instruction_pointer] == "[")
					{
						depth = depth + 1;
					}
					if (code[instruction_pointer] == "]")
					{
						if (depth == 0)
						{
							break;
						}
						
						depth = depth - 1;
					}
				}
			}
		}
		if (code[instruction_pointer] == "]")
		{
			if (data[data_pointer] != 0)
			{
				depth = 0;
				while (1 == 1)
				{
					instruction_pointer = instruction_pointer - 1;
					
					if (code[instruction_pointer] == "[")
					{
						if (depth == 0)
						{
							break;
						}
						
						depth = depth - 1;
					}
					if (code[instruction_pointer] == "]")
					{
						depth = depth + 1;
					}
				}
			}
		}
		
		instruction_pointer = instruction_pointer + 1;
	}
	
	if (output != "")
	{
		// Output has been generated, print it.
		println(output);
	}
}

function main()
{
	code = Brainfuck_CreateProgram_HelloWorld();
	data = Brainfuck_InitializeMemory(65536); // 64 KiB
	Brainfuck_Interpret(data, code);
}
