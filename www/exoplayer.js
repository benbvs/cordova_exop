//
//  exoplayer.js
//
// estat Cordova Plugin by Benjamin BEAUVAIS on 22/10/16.
//
// exoplayer Plugin for Cordova Android
// www.lanewscompany.com
//
// MIT Licensed


function exoplayer(){}

// Call this to register for push notifications and retreive a push Token
exoplayer.initPlayer = function(serial, success, fail) {
	cordova.exec(success, fail, "exoplayer", "initPlayer",[serial]);
};

exoplayer.play = function(url,success, fail) {
	cordova.exec(success, fail, "exoplayer", "play", [url]);
};

// Event spawned when a notification is received while the application is active
exoplayer.notificationCallback = function(notification) {
	var ev = document.createEvent('HTMLEvents');
	ev.notification = notification;
	ev.initEvent('push-notification', true, true, arguments);
	document.dispatchEvent(ev);
};

module.exports = exoplayer;
