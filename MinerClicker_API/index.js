import {initializeApp} from 'firebase/app';
import {collection, doc, getDocs, getFirestore, setDoc, deleteDoc } from 'firebase/firestore/lite';
import express from 'express';

const firebaseConfig = {
    apiKey: "AIzaSyCo01VJEfTAPGLsg8Carzzbe4gI0BQoidA",
    authDomain: "phonic-raceway-381611.firebaseapp.com",
    projectId: "phonic-raceway-381611",
    storageBucket: "phonic-raceway-381611.appspot.com",
    messagingSenderId: "976546491758",
    appId: "1:976546491758:web:9f389e72b3722248a3257f",
    measurementId: "G-QZ332ZQCCV"
};

const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

const usersCol = collection(db, 'usuarios');
// dataInicio, dataTermino, dia, id, quantExercicios, usuario

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
const addUser = async (email, id, name, password, phone) => {
    await setDoc(doc(db, "usuarios", id), {email, id, name, password, phone});
}
const getTrainings = async () => {
    const data = await getDocs(trainingsCol);
    return data.docs.map(doc => doc.data());
}
const existsTraining = async (id) => {
    const data = await getDocs(trainingsCol);
    let exists = false;
    data.docs.forEach(doc => {
        if (doc.data().id === id) {
            exists = true;
        }
    })
    return exists;
}
const addTraining = async (dataInicio, dataTermino, dia, id, quantExercicios, usuario) => {
    await setDoc(doc(db, "treinos", id), {dataInicio, dataTermino, dia, id, quantExercicios, usuario});
}
const getExercises = async () => {
    const data = await getDocs(exercisesCol);
    return data.docs.map(doc => doc.data());
}
const existsExercise = async (id) => {
    const data = await getDocs(exercisesCol);
    let exists = false;
    data.docs.forEach(doc => {
        if (doc.data().id === id) {
            exists = true;
        }
    })
    return exists;
}
const addExercise = async (descanso, descricao, nome, quant, treino) => {
    await setDoc(doc(db, "exercicios", id), {descanso, descricao, nome, quant, treino});
}

const expApp = express();
const port = 3000;

expApp.use(express.json());

expApp.get('/training/:user', (req, res) => {
    const user = req.params.user;
    getTrainings().then(trainings => {
        const training = trainings.find(training => training.usuario === user);
        if (training) {
            res.send(training);
        } else {
            res.send('Training does not exist');
        }
    });
});

expApp.post('/training', (req, res) => {
    const {dataInicio, dataTermino, dia, id, quantExercicios, usuario} = req.body;
    existsTraining(id).then(exists => {
        if (exists) {
            res.status(409)
            res.send('Training already exists');
        }
        else
        {
            const validDays = ['segunda', 'terca', 'quarta', 'quinta', 'sexta', 'sabado', 'domingo'];
            if (dataInicio.length < 10 || dataInicio.length > 10) {
                res.status(400)
                res.send('Start date must be 10 characters');
            }
            else if (dataTermino.length < 10 || dataTermino.length > 10) {
                res.status(400)
                res.send('End date must be 10 characters');
            }
            else if (!validDays.includes(dia)) {
                res.status(400)
                res.send('Day must be a valid day of the week');
            }
            else if (id < 0) {
                res.status(400)
                res.send('Id must be over 0');
            }
            else if (quantExercicios <= 0) {
                res.status(400)
                res.send('Quantity of exercises must be over 0');
            }
            else if (usuario.length < 4 || usuario.length > 15) {
                res.status(400)
                res.send('User must be over 4 characters and under 15');
            }
            else {
                addTraining(dataInicio, dataTermino, dia, id, quantExercicios, usuario).then( () => {
                    res.status(200);
                    res.send('Training added');
                });
            }
        }
    })
});

expApp.put('/training/:id', (req, res) => {
    const id = req.params.id;
    const {dataInicio, dataTermino, dia, quantExercicios, usuario} = req.body;

    existsTraining(id).then(exists => {
        if (exists) {
            const validDays = ['segunda', 'terca', 'quarta', 'quinta', 'sexta', 'sabado', 'domingo'];
            if (dataInicio.length < 10 || dataInicio.length > 10) {
                res.status(400)
                res.send('Start date must be 10 characters');
            }
            else if (dataTermino.length < 10 || dataTermino.length > 10) {
                res.status(400)
                res.send('End date must be 10 characters');
            }
            else if (!validDays.includes(dia)) {
                res.status(400)
                res.send('Day must be a valid day of the week');
            }
            else if (quantExercicios <= 0) {
                res.status(400)
                res.send('Quantity of exercises must be over 0');
            }
            else if (usuario.length < 4 || usuario.length > 15) {
                res.status(400)
                res.send('User must be over 4 characters and under 15');
            }
            else {
                addTraining(dataInicio, dataTermino, dia, id, quantExercicios, usuario).then( () => {
                    res.status(200);
                    res.send('Training updated');
                });
            }
        }
        else {
            res.send('Training does not exist');
            res.status(404)
        }
    });
});

expApp.delete('/training/:id', (req, res) => {
    const id = req.params.id;
    existsTraining(id).then(exists => {
        if (exists) {
            deleteDoc(doc(db, "treinos", id)).then( () => {
                res.status(200);
                res.send('Training deleted');
            });
            getExercises().then(exercises => {
                exercises.forEach(exercise => {
                    if (exercise.treino === id) {
                        deleteDoc(doc(db, "exercicios", exercise.id));
                    }
                })
            });
        }
        else {
            res.send('Training does not exist');
            res.status(404)
        }
    })
});



expApp.get('/user', (req, res) => {
    getUsers().then(users => res.send(users));
});

expApp.get('/user/:id', (req, res) => {
    const id = req.params.id;
    getUsers().then(users => {
        const user = users.find(user => user.id === id);
        if (user) {
            res.send(user);
        } else {
            res.send('User does not exist');
        }
    });
});

expApp.post('/user', (req, res) => {
    const {email, id, name, password, phone} = req.body;
    existsUser(id).then(exists => {
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
                    else if (id.length < 4 || id.length > 15) {
                        res.status(400)
                        res.send('Id must be over 4 characters and under 15');
                    }
                    else if (email.length < 5 || email.length > 30 || !email.includes('@')) {
                        res.status(400)
                        res.send('Email must be over 5 characters and under 30 and contain @');
                    }
                    else if (password.length < 8 || password.length > 30) {
                        res.status(400)
                        res.send('Password must be over 8 characters and under 30');
                    }
                    else if (phone.length < 9 || phone.length > 12) {
                        res.status(400)
                        res.send('Phone must have from 9 to 12 digits');
                    }
                    else {
                        addUser(email, id, name, password, phone).then( () => {
                            res.status(200);
                            res.send('User added');
                        });
                    }
                }
            })
        }
    })
});

expApp.put('/user/:id', (req, res) => {
    const id = req.params.id;
    const {email, name, password, phone} = req.body;

    existsUser(id).then(exists => {
        if (exists) {
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
            else if (phone.length < 9 || phone.length > 12) {
                res.status(400)
                res.send('Phone must have from 9 to 12 digits');
            }
            else {
                addUser(email, id, name, password, phone).then( () => {
                    res.status(200);
                    res.send('User updated');
                });
            }
        }
        else {
            res.send('User does not exist');
            res.status(404)
        }
    });
});

expApp.delete('/user/:id', (req, res) => {
    const id = req.params.id;
    existsUser(id).then(exists => {
        if (exists) {
            deleteDoc(doc(db, "user", id)).then( () => {
                res.status(200);
                res.send('User deleted');
            });
            // also delete all trainings from this user
            getTrainings().then(trainings => {
                trainings.forEach(training => {
                    if (training.usuario === id) {
                        deleteDoc(doc(db, "treinos", training.id));
                        // and all exercises for each training
                        getExercises().then(exercises => {
                            exercises.forEach(exercise => {
                                if (exercise.treino === training.id) {
                                    deleteDoc(doc(db, "exercicios", exercise.id));
                                }
                            })
                        });
                    }
                })
            });
        }
        else {
            res.send('User does not exist');
            res.status(404)
        }
    })
})



expApp.get('/exercise/:training', (req, res) => {
    const training = req.params.training;
    getExercises().then(exercises => {
        const exercise = exercises.find(exercise => exercise.treino === training);
        if (exercise) {
            res.send(exercise);
        } else {
            res.send('Exercise does not exist');
        }
    });
});

expApp.post('/exercise', (req, res) => {
    const {descanso, descricao, nome, quant, treino} = req.body;
    existsExercise(id).then(exists => {
        if (exists) {
            res.status(409)
            res.send('Exercise already exists');
        }
        else
        {
            if (descanso < 0) {
                res.status(400)
                res.send('Rest must be over 0');
            }
            else if (descricao.length < 10 || descricao.length > 100) {
                res.status(400)
                res.send('Description must be over 10 characters and under 100');
            }
            else if (nome.length < 3 || nome.length > 30) {
                res.status(400)
                res.send('Name must be over 3 characters and under 30');
            }
            else if (quant <= 0) {
                res.status(400)
                res.send('Quantity must be over 0');
            }
            else if (treino.length < 4 || treino.length > 15) {
                res.status(400)
                res.send('Training must be over 4 characters and under 15');
            }
            else {
                addExercise(descanso, descricao, nome, quant, treino).then( () => {
                    res.status(200);
                    res.send('Exercise added');
                });
            }
        }
    })
});

expApp.put('/exercise/:id', (req, res) => {
    const id = req.params.id;
    const {descanso, descricao, nome, quant, treino} = req.body;

    existsExercise(id).then(exists => {
        if (exists) {
            if (descanso < 0) {
                res.status(400)
                res.send('Rest must be over 0');
            }
            else if (descricao.length < 10 || descricao.length > 100) {
                res.status(400)
                res.send('Description must be over 10 characters and under 100');
            }
            else if (nome.length < 3 || nome.length > 30) {
                res.status(400)
                res.send('Name must be over 3 characters and under 30');
            }
            else if (quant <= 0) {
                res.status(400)
                res.send('Quantity must be over 0');
            }
            else if (treino > 0) {
                res.status(400)
                res.send('Training must be over 4 characters and under 15');
            }
            else {
                addExercise(descanso, descricao, nome, quant, treino).then( () => {
                    res.status(200);
                    res.send('Exercise updated');
                });
            }
        }
        else {
            res.send('Exercise does not exist');
            res.status(404)
        }
    });
});

expApp.delete('/exercise/:id', (req, res) => {
    const id = req.params.id;
    existsExercise(id).then(exists => {
        if (exists) {
            deleteDoc(doc(db, "exercicios", id)).then( () => {
                res.status(200);
                res.send('Exercise deleted');
            });
        }
        else {
            res.send('Exercise does not exist');
            res.status(404)
        }
    })
});

expApp.use((req, res) => {
    res.status(404).send('404 Not Found');
});

expApp.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`);
});