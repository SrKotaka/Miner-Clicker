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
    return data.docs.map(doc => doc.data());
}
const existsUser = async (id) => {
    const data = await getDocs(usersCol);
    let exists = false;
    data.docs.forEach(doc => {
        if (doc.data().id === id) {
            exists = true;
        }
    })
    return exists;
}
const existsUserEmail = async (email) => {
    const data = await getDocs(usersCol);
    let exists = false;
    data.docs.forEach(doc => {
        if (doc.data().email === email) {
            exists = true;
        }
    })
    return exists;
}
const addUser = async (email, name, password) => {
    await setDoc(doc(db, "usuarios", email), {email, name, password});
}

const expApp = express();
const port = 3000;

expApp.use(express.json());

expApp.get('/usuarios', (req, res) => {
    getUsers().then(users => res.send(users));
});

expApp.get('/usuarios/:email', (req, res) => {
    const email = req.params.email;
    getUsers().then(users => {
        const user = users.find(user => user.email === email);
        if (user) {
            res.send(user);
        } else {
            res.send('User does not exist');
        }
    });
});

expApp.post('/usuarios', (req, res) => {
    const {email, name, password, } = req.body;
    existsUser(email).then(exists => {
        if (exists) {
            res.status(409)
            res.send('User already exists');
        }
        else
        {
            existsUserEmail(email).then(exists => {
                if (exists) {
                    res.status(409)
                    res.send('Email already in use');
                }
                else
                {
                    if (name.length < 3 || name.length > 30) {
                        res.status(400)
                        res.send('Name must be over 3 characters and under 30');
                    }
                    else if (email.length < 5 || email.length > 30 || !email.includes('@')) {
                        res.status(400)
                        res.send('Email must be over 5 characters and under 30 and contain @');
                    }
                    else if (password.length < 8 || password.length > 30) {
                        res.status(400)
                        res.send('Password must be over 8 characters and under 30');
                    }
                    else {
                        addUser(email, name, password).then( () => {
                            res.status(200);
                            res.send('User added');
                        });
                    }
                }
            })
        }
    })
});

expApp.use((req, res) => {
    res.status(404).send('404 Not Found');
});

expApp.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`);
});