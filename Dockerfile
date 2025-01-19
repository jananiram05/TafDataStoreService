#FROM openjdk:17
FROM public.ecr.aws/docker/library/openjdk:17

COPY ./build/libs/dataStoreService.jar dataStoreService.jar
EXPOSE 8081
CMD ["java","-jar","dataStoreService.jar"]