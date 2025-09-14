import { Router } from "express";
import type { RubroController } from "./RubroController";

export function rubroRouter(rubroController: RubroController): Router {
    const router = Router();

    router.post('/create', rubroController.createRubro.bind(rubroController));
    router.get('/getAll', rubroController.getAllRubro.bind(rubroController));
    router.get('/getOneById/:id', rubroController.getOneByIdRubro.bind(rubroController));
    router.put('/update/:id', rubroController.updateRubro.bind(rubroController));
    router.delete('/delete/:id', rubroController.deleteRubro.bind(rubroController));

    return router;
}