# Multiplying big numbers


## Introduction
Multiplying large numbers is limited by the size of the values ​​represented in different programming languages, so if the maximum size is exceeded, other techniques must be used. This implementation proposes an algorithm that multiplies integers beyond the size of Java's integer types, using a *Divide and Conquer* programming approach.

## Instructions
To use the program, copy the *Multiply.jar* file to a folder of your choice. Then, open the operating system's terminal, navigate to the folder where the .jar file was copied, and run the program using the following syntax:

`java -jar multiply.jar [-t] [-h] [input_file] [output_file]`

The arguments are as follows:
* `-t`: Traces each step in a way that describes the decomposition into subnumbers.
* `-h`: Displays help and syntax for the execution command.

Opciones posibles:
* `java -jar multiply.jar -h`
* `java -jar multiply.jar input_file`
* `java -jar multiply.jar -t input_file`
* `java -jar multiply.jar -t input_file output_file`
* `java -jar multiply.jar input_file output_file`

* Considerations*:
* The input file consists of two values ​​separated by one or more spaces or line breaks. Additionally, the values ​​may contain a "+" or "-" sign. Two consecutive signs or spaces are not allowed before the first given value.
* Input and output files must have the .txt extension; otherwise, the application will generate an error.
* The limit for numbers that can be multiplied is two numbers of 2000 digits each. If either of them has more digits, the application will generate an error.

## Demo
Below are some sample images of the program.

* Example of solution output via the operating system terminal.

![terminal_output](Images/result_cmd.png)

* Example of solution output in an output file.

![output_file_example](Images/output_example.png)

* Example of a solution trace.

![trace_example](Images/trace_example.png)

## License
This project is licensed under the GNU General Public License v3 (GPLv3).

© 2025 Roberto Castillejo Embid.
See the [LICENSE](./LICENSE) file for more details.

