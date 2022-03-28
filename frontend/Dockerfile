# Stage 1: Compile and Build

# Use official node image as the base image
FROM node:lts-alpine as build

RUN npm install -g http-server

# Set the working directory
WORKDIR /app

COPY package*.json ./

# Add the source code to app
# COPY ./ /app

# Install all the dependencies
RUN npm install

COPY . .

# Generate the build of the application
RUN npm run build

# Stage 2: Serve app with nginx server

EXPOSE 8080
CMD ["http-server", "dist"]



