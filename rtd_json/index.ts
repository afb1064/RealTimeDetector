const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendAdminNotification = functions.database.ref('/MQ2').onUpdate((change: any, context: any) => {
    const kontrol = change.after.val();
    if(kontrol === 0){
        const notif = {notification:{
                title: 'WARNING',
                body: 'TOXIC GAS MEASURED',
                sound: 'fire' 
            }
    };

        return admin.messaging().sendToTopic("warning", notif).then((res: any) =>{
            console.log('SEND'+ res);
        }).catch((error: any) =>{
            console.log('AN ERROR OCCURED' + error);
        });
    }

});
