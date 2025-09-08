import { Router } from "express";
import type { RubroController } from "./RubroController";

export function rubroRouter(rubroController: RubroController): Router {
    const router = Router();

    router.post('/create', rubroController.createRubro.bind(rubroController));
    router.get('/getAll', rubroController.getAllRubro.bind(rubroController));
    router.get('/getOneById', rubroController.getOneByIdRubro.bind(rubroController));
    router.post('/update', rubroController.updateRubro.bind(rubroController));
    router.post('/delete', rubroController.deleteRubro.bind(rubroController));

    return router;
}