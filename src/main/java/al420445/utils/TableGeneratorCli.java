package al420445.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class for generating tables in text format.
 * Exemple usage:
 *
 *         Faker faker = new Faker(new Locale("fr-CA"));
 *
 *         TableGeneratorCli tableGenerator = new TableGeneratorCli();
 *
 *         List<String> headersList = new ArrayList<>();
 *         headersList.add("Id");
 *         headersList.add("F-Name");
 *         headersList.add("L-Name");
 *         headersList.add("Email");
 *
 *         List<List<String>> rowsList = new ArrayList<>();
 *
 *         for (int i = 0; i < 5; i++) {
 *             List<String> row = new ArrayList<>();
 *             row.add(UUID.randomUUID().toString());
 *             row.add(faker.name().firstName());
 *             row.add(faker.name().lastName());
 *             row.add(faker.internet().emailAddress());
 *
 *             rowsList.add(row);
 *         }
 *
 *         System.out.println(tableGenerator.generateTable(headersList, rowsList));
 *
 *+----------------------------------------+------------+------------+------------------------------+
 * |                   Id                   |   F-Name   |   L-Name   |            Email             |
 * +----------------------------------------+------------+------------+------------------------------+
 * |  caaa48d3-cf54-4abd-884b-904951f900be  |  Valentin  |  Maillard  |    paul.louis@hotmail.com    |
 * |  34dd8646-02f0-4c04-bca4-677680d2e2cb  |   Carla    |   Moréau   |  paul.leclercq@hotmail.com   |
 * |  ed2597f8-6e5b-4053-8b2a-a5bf9637bec3  |    Adam    |   Marié    |   matteo.nguyen@gmail.com    |
 * |  5a040a20-9d06-469b-871c-c9f16525ff77  |   Louna    |  Leclérc   |  lilou.roussel@hotmail.com   |
 * |  52b0b3d0-4ec3-4cfd-9478-a1c4cd6cb673  |  Raphaël   |  Lévêque   |   theo.bourgeois@yahoo.ca    |
 * +----------------------------------------+------------+------------+------------------------------+
 */
public class TableGeneratorCli {

    private int PADDING_SIZE = 2;
    private String NEW_LINE = "\n";
    private String TABLE_JOINT_SYMBOL = "+";
    private String TABLE_V_SPLIT_SYMBOL = "|";
    private String TABLE_H_SPLIT_SYMBOL = "-";

    public String generateTable(List<String> headersList, List<List<String>> rowsList,int... overRiddenHeaderHeight)
    {
        StringBuilder stringBuilder = new StringBuilder();

        int rowHeight = overRiddenHeaderHeight.length > 0 ? overRiddenHeaderHeight[0] : 1;

        Map<Integer,Integer> columnMaxWidthMapping = getMaximumWidhtofTable(headersList, rowsList);

        stringBuilder.append(NEW_LINE);
        stringBuilder.append(NEW_LINE);
        createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);
        stringBuilder.append(NEW_LINE);


        for (int headerIndex = 0; headerIndex < headersList.size(); headerIndex++) {
            fillCell(stringBuilder, headersList.get(headerIndex), headerIndex, columnMaxWidthMapping);
        }

        stringBuilder.append(NEW_LINE);

        createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);


        for (List<String> row : rowsList) {

            for (int i = 0; i < rowHeight; i++) {
                stringBuilder.append(NEW_LINE);
            }

            for (int cellIndex = 0; cellIndex < row.size(); cellIndex++) {
                fillCell(stringBuilder, row.get(cellIndex), cellIndex, columnMaxWidthMapping);
            }

        }

        stringBuilder.append(NEW_LINE);
        createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);
        stringBuilder.append(NEW_LINE);
        stringBuilder.append(NEW_LINE);

        return stringBuilder.toString();
    }

    private void fillSpace(StringBuilder stringBuilder, int length)
    {
        for (int i = 0; i < length; i++) {
            stringBuilder.append(" ");
        }
    }

    private void createRowLine(StringBuilder stringBuilder,int headersListSize, Map<Integer,Integer> columnMaxWidthMapping)
    {
        for (int i = 0; i < headersListSize; i++) {
            if(i == 0)
            {
                stringBuilder.append(TABLE_JOINT_SYMBOL);
            }

            for (int j = 0; j < columnMaxWidthMapping.get(i) + PADDING_SIZE * 2 ; j++) {
                stringBuilder.append(TABLE_H_SPLIT_SYMBOL);
            }
            stringBuilder.append(TABLE_JOINT_SYMBOL);
        }
    }


    private Map<Integer,Integer> getMaximumWidhtofTable(List<String> headersList, List<List<String>> rowsList)
    {
        Map<Integer,Integer> columnMaxWidthMapping = new HashMap<>();

        for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {
            columnMaxWidthMapping.put(columnIndex, 0);
        }

        for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {

            if(headersList.get(columnIndex).length() > columnMaxWidthMapping.get(columnIndex))
            {
                columnMaxWidthMapping.put(columnIndex, headersList.get(columnIndex).length());
            }
        }


        for (List<String> row : rowsList) {

            for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {

                if(row.get(columnIndex).length() > columnMaxWidthMapping.get(columnIndex))
                {
                    columnMaxWidthMapping.put(columnIndex, row.get(columnIndex).length());
                }
            }
        }

        for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {

            if(columnMaxWidthMapping.get(columnIndex) % 2 != 0)
            {
                columnMaxWidthMapping.put(columnIndex, columnMaxWidthMapping.get(columnIndex) + 1);
            }
        }


        return columnMaxWidthMapping;
    }

    private int getOptimumCellPadding(int cellIndex,int datalength,Map<Integer,Integer> columnMaxWidthMapping,int cellPaddingSize)
    {
        if(datalength % 2 != 0)
        {
            datalength++;
        }

        if(datalength < columnMaxWidthMapping.get(cellIndex))
        {
            cellPaddingSize = cellPaddingSize + (columnMaxWidthMapping.get(cellIndex) - datalength) / 2;
        }

        return cellPaddingSize;
    }

    private void fillCell(StringBuilder stringBuilder,String cell,int cellIndex,Map<Integer,Integer> columnMaxWidthMapping)
    {

        int cellPaddingSize = getOptimumCellPadding(cellIndex, cell.length(), columnMaxWidthMapping, PADDING_SIZE);

        if(cellIndex == 0)
        {
            stringBuilder.append(TABLE_V_SPLIT_SYMBOL);
        }

        fillSpace(stringBuilder, cellPaddingSize);
        stringBuilder.append(cell);
        if(cell.length() % 2 != 0)
        {
            stringBuilder.append(" ");
        }

        fillSpace(stringBuilder, cellPaddingSize);

        stringBuilder.append(TABLE_V_SPLIT_SYMBOL);

    }

    public static void main(String[] args) {
        TableGeneratorCli tableGeneratorCli = new TableGeneratorCli();

        List<String> headersList = new ArrayList<>();
        headersList.add("Id");
        headersList.add("F-Name");
        headersList.add("L-Name");
        headersList.add("Email");

        List<List<String>> rowsList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            List<String> row = new ArrayList<>();
            row.add(UUID.randomUUID().toString());
            row.add(UUID.randomUUID().toString());
            row.add(UUID.randomUUID().toString());
            row.add(UUID.randomUUID().toString());

            rowsList.add(row);
        }

        System.out.println(tableGeneratorCli.generateTable(headersList, rowsList));
    }
}