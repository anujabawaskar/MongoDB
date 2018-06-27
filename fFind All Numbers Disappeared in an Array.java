class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        int[] arr = new int[nums.length + 1];
        arr[0] = 1;
        List<Integer> answer = new ArrayList<Integer>();
        for(int n : nums) 
            arr[n] = 1;
        
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == 0)
                answer.add(i);
        }
        return answer;
    }
}
