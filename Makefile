.PHONY: all build run

all: build run

build:
	docker build -t techcompare --progress plain .

run:
	docker run -it --rm -p 3000:3000 techcompare