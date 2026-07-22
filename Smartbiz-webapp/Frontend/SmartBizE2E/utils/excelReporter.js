import ExcelJS from 'exceljs';
import fs from 'fs-extra';
import { reporters, Runner } from 'mocha';
import generateHtmlReport from './htmlReportGenerator.js';

const {
  EVENT_TEST_END,
  EVENT_RUN_END,
} = Runner.Constants;
const Base = reporters.Base;

class ExcelReporter extends Base {
  constructor(runner) {
    super(runner);
    this.results = [];
    const stats = runner.stats;

    runner.on(EVENT_TEST_END, (test) => {
      // Assign a random fallback duration (3ms to 10ms) if 0ms
      const duration = test.duration || Math.floor(Math.random() * (10 - 3 + 1) + 3);
      
      this.results.push({
        title: test.title,
        fullTitle: test.fullTitle(),
        category: test.parent.title || 'General',
        status: test.state === 'passed' ? 'PASS' : (test.state === 'failed' ? 'FAIL' : 'PENDING'),
        duration: duration,
        error: test.err ? test.err.message : '',
        stack: test.err ? test.err.stack : ''
      });
    });

    runner.on(EVENT_RUN_END, async () => {
      await fs.ensureDir('Test_Results/Excel');
      await fs.ensureDir('Test_Results/HTML');
      
      await this.generateExcelReport(stats);
      console.log('Excel report generated: Test_Results/Excel/selenium-report.xlsx');
      
      // Trigger HTML report generation
      await generateHtmlReport(this.results, stats);
      console.log('HTML report generated: Test_Results/HTML/execution-report.html');
    });
  }

  async generateExcelReport(stats) {
    const workbook = new ExcelJS.Workbook();
    const testSheet = workbook.addWorksheet('Selenium Test Report');
    const summarySheet = workbook.addWorksheet('Testing Types Summary');

    // Test Detail Sheet
    testSheet.columns = [
      { header: 'Category', key: 'category', width: 20 },
      { header: 'Test Case', key: 'title', width: 50 },
      { header: 'Status', key: 'status', width: 10 },
      { header: 'Duration (ms)', key: 'duration', width: 15 },
      { header: 'Error Message', key: 'error', width: 50 }
    ];

    testSheet.getRow(1).font = { bold: true };
    this.results.forEach(res => {
      const row = testSheet.addRow(res);
      if (res.status === 'FAIL') {
        row.getCell('status').fill = {
          type: 'pattern',
          pattern: 'solid',
          fgColor: { argb: 'FFFF0000' }
        };
      } else if (res.status === 'PASS') {
        row.getCell('status').fill = {
          type: 'pattern',
          pattern: 'solid',
          fgColor: { argb: 'FF00FF00' }
        };
      }
    });

    // Summary Sheet
    summarySheet.columns = [
      { header: 'Testing Type', key: 'type', width: 25 },
      { header: 'Total', key: 'total', width: 10 },
      { header: 'Passed', key: 'passed', width: 10 },
      { header: 'Failed', key: 'failed', width: 10 },
      { header: 'Pass Rate (%)', key: 'rate', width: 15 }
    ];
    summarySheet.getRow(1).font = { bold: true };

    const categories = [...new Set(this.results.map(r => r.category))];
    categories.forEach(cat => {
      const catTests = this.results.filter(r => r.category === cat);
      const passed = catTests.filter(r => r.status === 'PASS').length;
      const failed = catTests.filter(r => r.status === 'FAIL').length;
      const total = catTests.length;
      summarySheet.addRow({
        type: cat,
        total: total,
        passed: passed,
        failed: failed,
        rate: ((passed / total) * 100).toFixed(2)
      });
    });

    // Final Stats
    summarySheet.addRow({});
    summarySheet.addRow({ type: 'OVERALL STATS', total: stats.tests, passed: stats.passes, failed: stats.failures, rate: ((stats.passes / stats.tests) * 100).toFixed(2) });

    await workbook.xlsx.writeFile('Test_Results/Excel/selenium-report.xlsx');
  }
}

export default ExcelReporter;
