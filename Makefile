main = ex3.EmailClient

all : build
	(cd bin; java -cp .:../lib/mail.jar $(main))

build : clean
	javac -cp .:lib/mail.jar -d bin/ `find src/ -name \*.java`

clean :
	rm -rf bin/
	mkdir bin
