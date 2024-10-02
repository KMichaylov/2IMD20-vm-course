function null()
{
}

function noremainder(val, divisor)
{
	return val - divisor * (val / divisor) == 0;
}

function append(left, right)
{
	if (left == null()) {
		return right;
	} else {
		return left + right;
	}
}

function main()
{
	i = 1;
	while (i < 1000) {
		output = null();
		if (noremainder(i, 3)) {
			output = append(output, "Fizz");
		}
		if (noremainder(i, 5)) {
			output = append(output, "Buzz");
		}
		if (output == null()) {
			output = i;
		}
		println(output);
		i = i + 1;
	}
}
