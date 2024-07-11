build_docker:
	docker build -t fsss-frontend .

run_docker:
	docker run -d -p 80:80 fsss-frontend

stop_docker:
	docker ps -q --filter ancestor="fsss-frontend" | xargs -r docker stop
