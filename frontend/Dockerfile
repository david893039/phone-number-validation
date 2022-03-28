# Stage 1: Compile and Build angular codebase

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

EXPOSE 8080
CMD ["http-server", "dist"]

# Stage 2: Serve app with nginx server

# Use official nginx image as the base image
# FROM nginx:stable-alpine

# Copy the build output to replace the default nginx contents.
# COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80
# EXPOSE 8080

# CMD ["nginx","-g","daemon off;"]