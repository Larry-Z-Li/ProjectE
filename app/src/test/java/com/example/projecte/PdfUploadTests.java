package com.example.projecte;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PdfUploadTests {

    private String title1 = "Introduction to Android";
    private String url1 = "https://firebasestorage.googleapis.com/v0/b/projecte.appspot.com/o/pdf1.pdf?alt=media&token=abc123";
    private long timestamp1 = 1697059200000L;
    private String resourceType1 = "Books";
    private String groupName1 = "GroupA";

    private String title2 = "Advanced Firebase";
    private String url2 = "https://firebasestorage.googleapis.com/v0/b/projecte.appspot.com/o/pdf2.pdf?alt=media&token=def456";
    private long timestamp2 = 1697145600000L;
    private String resourceType2 = "Articles";
    private String groupName2 = "GroupB";

    private PdfItem pdfItem1;
    private PdfItem pdfItem2;
    private List<PdfItem> pdfList;

    @Before
    public void setUp() {
        pdfItem1 = new PdfItem(title1, url1, timestamp1, resourceType1, groupName1);
        pdfItem2 = new PdfItem(title2, url2, timestamp2, resourceType2, groupName2);
        pdfList = new ArrayList<>();
    }

    @Test
    public void testPdfItemCreation() {
        Assert.assertEquals(title1, pdfItem1.getTitle());
        Assert.assertEquals(url1, pdfItem1.getUrl());
        Assert.assertEquals(timestamp1, pdfItem1.getTimestamp());
        Assert.assertEquals(resourceType1, pdfItem1.getResourceType());
        Assert.assertEquals(groupName1, pdfItem1.getGroupName());

        Assert.assertEquals(title2, pdfItem2.getTitle());
        Assert.assertEquals(url2, pdfItem2.getUrl());
        Assert.assertEquals(timestamp2, pdfItem2.getTimestamp());
        Assert.assertEquals(resourceType2, pdfItem2.getResourceType());
        Assert.assertEquals(groupName2, pdfItem2.getGroupName());
    }

    @Test
    public void testAddingPdfItems() {
        Assert.assertEquals(0, pdfList.size());

        pdfList.add(pdfItem1);
        Assert.assertEquals(1, pdfList.size());
        Assert.assertEquals(pdfItem1, pdfList.get(0));

        pdfList.add(pdfItem2);
        Assert.assertEquals(2, pdfList.size());
        Assert.assertEquals(pdfItem2, pdfList.get(1));
    }

    @Test
    public void testFilterByResourceType() {
        pdfList.add(pdfItem1);
        pdfList.add(pdfItem2);

        List<PdfItem> filteredList = filterByResourceType(pdfList, "Books");
        Assert.assertEquals(1, filteredList.size());
        Assert.assertEquals(pdfItem1, filteredList.get(0));
    }

    @Test
    public void testFilterByGroupName() {
        pdfList.add(pdfItem1);
        pdfList.add(pdfItem2);

        List<PdfItem> filteredList = filterByGroupName(pdfList, "GroupB");
        Assert.assertEquals(1, filteredList.size());
        Assert.assertEquals(pdfItem2, filteredList.get(0));
    }

    @Test
    public void testUploadPdfWithEmptyTitle() {
        String emptyTitle = "";
        PdfItem invalidPdf = new PdfItem(emptyTitle, url1, timestamp1, resourceType1, groupName1);

        boolean isValid = validatePdfItem(invalidPdf);
        Assert.assertFalse("PDF with empty title should be invalid", isValid);
    }


    private List<PdfItem> filterByResourceType(List<PdfItem> list, String resourceType) {
        List<PdfItem> filtered = new ArrayList<>();
        for (PdfItem item : list) {
            if (item.getResourceType().equals(resourceType)) {
                filtered.add(item);
            }
        }
        return filtered;
    }


    private List<PdfItem> filterByGroupName(List<PdfItem> list, String groupName) {
        List<PdfItem> filtered = new ArrayList<>();
        for (PdfItem item : list) {
            if (item.getGroupName().equals(groupName)) {
                filtered.add(item);
            }
        }
        return filtered;
    }
    
    private boolean validatePdfItem(PdfItem item) {
        if (item.getTitle() == null || item.getTitle().isEmpty()) {
            return false;
        }
        if (item.getUrl() == null || item.getUrl().isEmpty()) {
            return false;
        }
        if (item.getResourceType() == null || item.getResourceType().isEmpty()) {
            return false;
        }
        if (item.getGroupName() == null || item.getGroupName().isEmpty()) {
            return false;
        }
        return true;
    }
}

