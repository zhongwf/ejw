## Problem
On IE9-, ajax uploading a file would end up the browser asking you to save a downloaded file, like is said in [this post](http://stackoverflow.com/questions/5388893/ie9-json-data-do-you-want-to-open-or-save-this-file/12315807#12315807).

## Research

A normal ajax request expecting a response type of "application/json" is handled correctly on IE9-:

![](https://github.com/octopusthu/ejw/blob/master/notes/resources/normal-ajax-on-ie9.png)

However, an ajax request of content-type "multipart/form-data" on IE9-, if responded by the server with a content-type of "application/json", will be interpreted by the browser as a downloaded file:

![](https://github.com/octopusthu/ejw/blob/master/notes/resources/multipart-ajax-on-ie9.png)

Many existing posts([this](http://stackoverflow.com/questions/8151138/ie-tries-to-download-json-response-while-submitting-jquery-multipart-form-data-c), [this](http://stackoverflow.com/questions/13943439/json-response-download-in-ie710) and [this](http://stackoverflow.com/questions/10044986/multipart-form-post-causes-firefox-to-prompt-json-save)) discuss this problem and come to a common [solution](http://stackoverflow.com/a/13944206/5771608). However, the underlying reason behind this odd behavior of IE9- is still unclear.

## Solution
Set the response type to "text/html" on the server, and parse the string back to json on the client.
