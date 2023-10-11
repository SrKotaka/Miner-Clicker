import {initializeApp} from 'firebase/app';
import {collection, doc, getDocs, getFirestore, setDoc } from 'firebase/firestore/lite';
import express from 'express';

const firebaseConfig = {
    apiKey: "AIzaSyCIgD7Vpg5SAPo2XBfY74X7-n5zVxbZ4rU",
    authDomain: "clicker-e7248.firebaseapp.com",
    projectId: "clicker-e7248",
    storageBucket: "clicker-e7248.appspot.com",
    messagingSenderId: "68584008771",
    appId: "1:68584008771:web:9f7c00c1966d3b3253f8f6",
    measurementId: "G-LKY7J41DBZ"
};

const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

const usersCol = collection(db, 'usuarios');

const getUsers = async () => {
    const data = await getDocs(usersCol);
    const users = [];
    data.docs.forEach(doc => {
        users.push(doc.data());
    });
    return users;
}
const existsUserEmail = async (email) => {
    const users = await getUsers();
    return users.some(user => user.email === email);
}
const addUser = async (email, name, password) => {
    await setDoc(doc(usersCol, email), {
        email: email,
        name: name,
        password: password
    });
}



const expApp = express();
const port = 3000;

expApp.use(express.json());

expApp.get('/usuarios', (req, res) => {
    getUsers().then(users => {
        res.json(users);
    });
});

expApp.get('/usuarios/:email', (req, res) => {
    const email = req.params.email;
    getUsers().then(users => {
        const user = users.find(user => user.email === email);
        if (user) {
            res.json(user);
        } else {
            res.status(404).send('404 Not Found');
        }
    });
});

expApp.post('/usuarios', (req, res) => {
    const email = req.body.email;
    const name = req.body.name;
    const password = req.body.password;
    if (email && name && password) {
        existsUserEmail(email).then(exists => {
            if (exists) {
                res.status(409).send('409 Conflict');
            } else {
                addUser(email, name, password).then(() => {
                    res.status(201).send('201 Created');
                });
            }
        });
    } else {
        res.status(400).send('400 Bad Request');
    }
});

expApp.use((req, res) => {
    res.status(404).send('404 Not Found');
});

expApp.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`);
});