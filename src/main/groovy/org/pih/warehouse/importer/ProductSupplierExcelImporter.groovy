/**
 * Copyright (c) 2012 Partners In Health.  All rights reserved.
 * The use and distribution terms for this software are covered by the
 * Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 * which can be found in the file epl-v10.html at the root of this distribution.
 * By using this software in any fashion, you are agreeing to be bound by
 * the terms of this license.
 * You must not remove this notice, or any other, from this software.
 **/
package org.pih.warehouse.importer

import grails.util.Holders
import org.grails.plugins.excelimport.ExpectedPropertyType
import org.pih.warehouse.data.ProductSupplierDataService

class ProductSupplierExcelImporter extends AbstractExcelImporter {

    def excelImportService
    ProductSupplierDataService productSupplierDataService

    static Map columnMap = [
            sheet    : 'Sheet1',
            startRow : 1,
            columnMap: [
                    'A': 'id',
                    'B': 'code',
                    'C': 'productCode',
                    'D': 'legacyProductCode',
                    'E': 'productName',
                    'F': 'description',
                    'G': 'supplierId',
                    'H': 'supplierName',
                    'I': 'supplierCode',
                    'J': 'supplierProductName',
                    'K': 'manufacturerId',
                    'L': 'manufacturerName',
                    'M': 'manufacturerCode',
                    'N': 'manufacturerProductName',
                    'O': 'unitPrice',
                    'P': 'standardLeadTimeDays',
                    'Q': 'preferenceTypeCode',
                    'R': 'ratingTypeCode',
                    'S': 'comments',
            ]
    ]

    static Map propertyMap = [
            id                     : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            code                   : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            productCode            : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            legacyProductCode      : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            productName            : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            description            : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            supplierId             : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            supplierName           : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            supplierCode           : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            supplierProductName    : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            manufacturerId         : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            manufacturerName       : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            manufacturerCode       : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            manufacturerProductName: ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            unitPrice              : ([expectedType: ExpectedPropertyType.IntType, defaultValue: null]),
            standardLeadTimeDays   : ([expectedType: ExpectedPropertyType.IntType, defaultValue: null]),
            preferenceTypeCode     : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            ratingTypeCode         : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            comments               : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null])
    ]


    ProductSupplierExcelImporter(String fileName) {
        super(fileName)
    }

    def getDataService() {
        return Holders.getGrailsApplication().getMainContext().getBean("productSupplierDataService")
    }


    List<Map> getData() {
        return excelImportService.convertColumnMapConfigManyRows(workbook, columnMap, null, propertyMap)
    }


    void validateData(ImportDataCommand command) {
        dataService.validate(command)
    }

    /**
     * Import data from given inventoryMapList into database.
     *
     * @param location
     * @param inventoryMapList
     * @param errors
     */
    void importData(ImportDataCommand command) {
        dataService.process(command)
    }

}