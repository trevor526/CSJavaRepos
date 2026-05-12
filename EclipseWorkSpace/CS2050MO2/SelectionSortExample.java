public class SelectionSortExample {

    public static void main(String[] args) {

        int[] numbers = {64, 25, 12, 22, 11};

        // Call the selection sort method
        selectionSort(numbers);

        // Print the sorted array
        System.out.println("Sorted array:");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }

    public static void selectionSort(int[] arr) {

        int n = arr.length;

        // Outer loop moves the boundary of the unsorted portion
        for (int i = 0; i < n - 1; i++) {

            // Assume the smallest value is at position i
            int minIndex = i;

            // Inner loop searches for the smallest value in the remaining array
            for (int j = i + 1; j < n; j++) {

                // If we find a smaller element, update minIndex
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap the smallest element with the element at position i
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}