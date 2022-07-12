const express = require('express');
const router = express.Router();
const peopleController = require('../controllers/peopleController');
const authController = require('../controllers/authController');
const isAuth = require("../middleware/isAuth")
const isAdmin = require('../middleware/isAdmin');

router.get('/people', peopleController.people_index);

router.post('/people/add', isAdmin, peopleController.people_create_post);

router.delete('/people/delete/:id', isAuth, peopleController.people_delete);

router.get('/people/edit/:id', isAuth, peopleController.people_edit_get);

router.post('/people/edit/:id', isAuth, peopleController.people_edit_post);

router.post('/login', authController.login);

router.get('/logout', authController.logout);

module.exports = router;