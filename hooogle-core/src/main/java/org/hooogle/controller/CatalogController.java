package org.hooogle.controller;

import org.hooogle.model.Catalog;
import org.hooogle.service.CatalogService;
import org.hooogle.service.OperatorService;
import org.hooogle.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-28
 * Time: 上午9:57
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("catalog")
public class CatalogController {
    @Autowired
    private ServerService serverService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private OperatorService operatorService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    Long save(@RequestBody Catalog catalog, HttpServletRequest request) throws Exception {
        if (catalog.getOperatorId() == null) {
            catalog.setOperatorId(operatorService.getLoginOperatorId());
        }
        return catalogService.save(catalog);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Catalog> queryByParentId(@RequestParam(required = false) Long parentId, HttpServletRequest request) throws Exception {
        Long operatorId = operatorService.getLoginOperatorId();
        return catalogService.queryByParentIdAndOperatorId(parentId, operatorId);
    }

    @RequestMapping(value = "/{catalogId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    void deleteById(@PathVariable Long catalogId, HttpServletRequest request) throws Exception {
        Long operatorId = operatorService.getLoginOperatorId();
        catalogService.deleteByIdAndOperator(catalogId, operatorId);
    }
}
