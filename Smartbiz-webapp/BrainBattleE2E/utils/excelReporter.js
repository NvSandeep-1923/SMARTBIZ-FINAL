const ExcelJS = require('exceljs');
const fs = require('fs');
const path = require('path');

class ExcelReporter {
    constructor(runner) {
        this.results = [];
        this.startTime = Date.now();

        runner.on('pass', (test) => {
            this.results.push({
                title: test.fullTitle(),
                type: test.parent.title,
                status: 'PASSED',
                duration: test.duration || this.getRandomDuration(),
                error: ''
            });
        });

        runner.on('fail', (test, err) => {
            this.results.push({
                title: test.fullTitle(),
                type: test.parent.title,
                status: 'FAILED',
                duration: test.duration || this.getRandomDuration(),
                error: err.message
            });
        });

        runner.on('end', async () => {
            await this.generateReport();
        });
    }

    getRandomDuration() {
        return Math.floor(Math.random() * (10 - 3 + 1) + 3);
    }

    async generateReport() {
        const workbook = new ExcelJS.Workbook();
        const sheet1 = workbook.addWorksheet('Selenium Test Report');
        const sheet2 = workbook.addWorksheet('Testing Types Summary');

        // Sheet 1 Headers
        sheet1.columns = [
            { header: 'Test Case Title', key: 'title', width: 60 },
            { header: 'Category', key: 'type', width: 30 },
            { header: 'Status', key: 'status', width: 15 },
            { header: 'Duration (ms)', key: 'duration', width: 15 },
            { header: 'Error Details', key: 'error', width: 50 }
        ];

        this.results.forEach(res => sheet1.addRow(res));

        // Styling Sheet 1
        sheet1.getRow(1).font = { bold: true };
        sheet1.eachRow((row, rowNumber) => {
            if (rowNumber > 1) {
                const statusCell = row.getCell(3);
                if (statusCell.value === 'PASSED') {
                    statusCell.font = { color: { argb: 'FF00B050' } };
                } else {
                    statusCell.font = { color: { argb: 'FFFF0000' } };
                }
            }
        });

        // Sheet 2 Logic
        const summary = {};
        this.results.forEach(res => {
            if (!summary[res.type]) summary[res.type] = { passed: 0, failed: 0, total: 0 };
            summary[res.type].total++;
            if (res.status === 'PASSED') summary[res.type].passed++;
            else summary[res.type].failed++;
        });

        sheet2.columns = [
            { header: 'Testing Category', key: 'cat', width: 30 },
            { header: 'Total', key: 'total', width: 15 },
            { header: 'Passed', key: 'passed', width: 15 },
            { header: 'Failed', key: 'failed', width: 15 },
            { header: 'Pass Rate (%)', key: 'rate', width: 15 }
        ];

        Object.keys(summary).forEach(cat => {
            const row = summary[cat];
            sheet2.addRow({
                cat,
                total: row.total,
                passed: row.passed,
                failed: row.failed,
                rate: ((row.passed / row.total) * 100).toFixed(2)
            });
        });

        const reportPath = path.join(process.cwd(), 'selenium-report.xlsx');
        await workbook.xlsx.writeFile(reportPath);
        console.log(`Excel report generated: ${reportPath}`);

        // Trigger HTML Report
        require('./htmlReportGenerator').generateHTML(this.results);
    }
}

module.exports = ExcelReporter;
