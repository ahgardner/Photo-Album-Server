# Photo-Album-Server  

Sample RESTful server in Clojure based on ring.  

Requires a root folder, with one or more subfolders containing images files.  Edit config.edn to set the root folder.  

Services so far:  

| Command | Description |
| --- | --- |
| GET / | Retrieves album list |  
| GET /album | Retrieves list of images in album |   
| GET /album/image | Downloads the image |   
| PUT /album/image?alias=x | Saves a user-defined name x for the image |   
